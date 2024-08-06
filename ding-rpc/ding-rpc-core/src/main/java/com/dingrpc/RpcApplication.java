package com.dingrpc;


import com.dingrpc.config.RegistryConfig;
import com.dingrpc.constant.RpcConstant;
import com.dingrpc.registry.Registry;
import com.dingrpc.registry.RegistryFactory;
import com.dingrpc.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;


import com.dingrpc.config.RpcConfig;

/**
 * RPC 框架应用
 * 相当于啊 handle，存放了项目全局用到的变量，
 * 采用双检索单例模式实现  ：不用每次需要配置文件就去加载，只需要在启动的时候加载一次，减少性能消耗开销
 * @author  ding
 *
 */

@Slf4j
public class RpcApplication {

    private static volatile RpcConfig rpcConfig;

    /**
     *
     * 框架初始化，支持传入自定义配置
     * @param newRpcConfig
     */
    public static void init(RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        log.info("rpc init,config = {}", newRpcConfig.toString());
        //注册中心初始化
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        registry.init(registryConfig);
        log.info("Registry init,config  = {}", registryConfig);


        //创建并注册Shutdown Hook ，JVM退出时执行操作  注册中心的 destory  方法
        Runtime.getRuntime().addShutdownHook(new Thread(registry::destory));

    }

    /**
     * 初始化
     */
    public static void init(){
        RpcConfig newRpcConfig;
        try {
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAUTL_CONFIG_PREFIX);
        } catch (Exception e) {
            //配置加载失败，使用默认值
            newRpcConfig = new RpcConfig();
        }
        init(newRpcConfig);
    }

    /**
     *
     * 双检索单例模式   懒惰单例模式  如果拿到了class  不需要重新加载
     *  获取配置文件
     * @return
     */

    public static RpcConfig getRpcConfig(){
        if(rpcConfig == null){
            synchronized (RpcApplication.class){
                if(rpcConfig == null){
                    init();
                }
            }
        }
        return rpcConfig;
    }

}

