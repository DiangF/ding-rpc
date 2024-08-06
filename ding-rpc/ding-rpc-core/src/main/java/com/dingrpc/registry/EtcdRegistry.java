package com.dingrpc.registry;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import cn.hutool.json.JSONUtil;
import com.dingrpc.config.RegistryConfig;
import com.dingrpc.model.ServiceMetaInfo;
import io.etcd.jetcd.*;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;
import io.etcd.jetcd.watch.WatchEvent;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * 注册中心
 * @author ding
 */
public class EtcdRegistry  implements  Registry{

    private Client client;  //进行一些服务客户端操作
    private KV kvClient;    //获取键值对   操作键值对

    /**
     * 设置服务的根节点
     */
    private  static  final String ETCD_ROOT_PATH = "/rpc/";   //可以用来区分不同的项目

    /**
     * 本机注册节点的 key集合（维护续期）
     */
    private final Set<String> localRegisterNodeKeySet = new HashSet<>();

    /**
     * 注册中心缓存
     */
    private final RegistryServiceCache registryServiceCache = new RegistryServiceCache();

    /**
     * 正在监听的 Key 集合   使用 ConcurrentHashMap 防止并发冲突
     */
    private final Set<String> watchingKeySet = new ConcurrentHashSet<>();


    /**
     *
     * @param registryConfig
     */
    @Override
    public void init(RegistryConfig registryConfig) {
        client  = Client.builder().endpoints(registryConfig.getAddress())
                .connectTimeout(Duration.ofMillis(registryConfig.getTimeout())).build();
        kvClient = client.getKVClient();
        heartBert();   //调用心跳检测

    }


    /**
     * 注册服务
     * @param serviceMetaInfo
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Override
    public void register(ServiceMetaInfo serviceMetaInfo) throws Exception {
        //创建lease 和KV 客户端
        Lease leaseClient = client.getLeaseClient();

        //创建一个30秒的租约
        long leaseId = leaseClient.grant(30).get().getID();

        //设置要存储的键值对
        String registerKey = ETCD_ROOT_PATH + serviceMetaInfo.getServiceNodeKey();

        ByteSequence key = ByteSequence.from(registerKey, StandardCharsets.UTF_8);

        ByteSequence value = ByteSequence.from(JSONUtil.toJsonStr(serviceMetaInfo),StandardCharsets.UTF_8);
        //将键值对 与 租约相关联，并设置过期时间
        PutOption putOption = PutOption.builder().withLeaseId(leaseId).build();
        kvClient.put(key,value,putOption).get();

        //添加节点信息到本地缓存
        localRegisterNodeKeySet.add(registerKey);   //实现续期
    }


    /**
     * 注销服务  删除key
     * @param serviceMetaInfo
     */
    @Override
    public void unRegister(ServiceMetaInfo serviceMetaInfo) {
        String registerKEY = ETCD_ROOT_PATH+
                serviceMetaInfo.getServiceNodeKey();
        kvClient.delete(ByteSequence.from(registerKEY,StandardCharsets.UTF_8));
        // 服务注销后从本地对应销毁   缓存中移除
        localRegisterNodeKeySet.remove(registerKEY);
    }


    /**
     * 服务发现
     * @param serviceKey 服务键名
     * @return
     */
    @Override
    public List<ServiceMetaInfo> serviceDiscovery(String serviceKey)  {
        //优先从本地缓存中取服务
        List<ServiceMetaInfo> cacheServiceMetaInfoList = registryServiceCache.readCache();
        if(cacheServiceMetaInfoList != null){
            return cacheServiceMetaInfoList;
        }
        // 前缀搜索  结尾一定要加 ‘/’
        String searchPrefix = ETCD_ROOT_PATH + serviceKey +"/";

        try {
            //前缀查询
            GetOption getOption = GetOption.builder().isPrefix(true).build();
            List<KeyValue> keyValues = kvClient.get(ByteSequence.from(searchPrefix, StandardCharsets.UTF_8),
                    getOption).get().getKvs();
            //解析服务信息
            List<ServiceMetaInfo> serviceMetaInfoList = keyValues.stream().map(keyValue -> {
                String key = keyValue.getKey().toString(StandardCharsets.UTF_8);
                watch(key);
                String value = keyValue.getValue().toString(StandardCharsets.UTF_8);
                return JSONUtil.toBean(value,ServiceMetaInfo.class);
            }).collect(Collectors.toList());
            //写入服务缓存
            registryServiceCache.writeCache(serviceMetaInfoList);
            return serviceMetaInfoList;
        } catch (Exception e) {
            throw new RuntimeException("获取服务列表失败",e);
        }
    }

    /**
     * 心跳检测
     */
    @Override
    public void heartBert() {

        // 10S 续约一次
        CronUtil.schedule("*/10 * * * * *", new Task() {
            @Override
            public void execute() {
                // 遍历本节点所有的 key
                for (String key : localRegisterNodeKeySet) {
                    try {
                        List<KeyValue> keyValues = kvClient.get(ByteSequence.from(key, StandardCharsets.UTF_8))
                                .get()
                                .getKvs();
                        // 该节点已过期（需要重启节点才能重新注册）
                        if (CollUtil.isEmpty(keyValues)) {
                            continue;
                        }
                        // 节点未过期，重新注册（相当于续签）
                        KeyValue keyValue = keyValues.get(0);
                        String value = keyValue.getValue().toString(StandardCharsets.UTF_8);
                        ServiceMetaInfo serviceMetaInfo = JSONUtil.toBean(value, ServiceMetaInfo.class);
                        register(serviceMetaInfo);
                    } catch (Exception e) {
                        throw new RuntimeException(key + "续签失败", e);
                    }
                }
            }
        });

        // 支持秒级别定时任务
        CronUtil.setMatchSecond(true);
        CronUtil.start();
    }


    /**
     * 监听  (消费端)
     * @param serviceNodeKey
     */
    @Override
    public void watch(String serviceNodeKey) {
        Watch watchClient = client.getWatchClient();
        // 之前未被监听，开启监听
        boolean newWatch = watchingKeySet.add(serviceNodeKey);
        if (newWatch) {
            watchClient.watch(ByteSequence.from(serviceNodeKey, StandardCharsets.UTF_8), response -> {
                for (WatchEvent event : response.getEvents()) {
                    switch (event.getEventType()) {
                        // key 删除时触发
                        case DELETE:
                            // 清理注册服务缓存
                            registryServiceCache.clearCache();
                            break;
                        case PUT:
                        default:
                            break;
                    }
                }
            });
        }


    }


    /**
     * 服务销毁
     */
    @Override
    public void destory() {
        System.out.println("当前节点下线");
        //下线节点
        // 遍历本节点的所有Key
          for(String key:localRegisterNodeKeySet){
              try {
                  kvClient.delete(ByteSequence.from(key,StandardCharsets.UTF_8)).get();
              } catch (Exception e) {
                  throw new RuntimeException(key+ "节点下线失败");
              }
          }
          //释放资源
        if(kvClient != null){
            kvClient.close();
        }
        if(client != null){
            client.close();
        }

    }
}
