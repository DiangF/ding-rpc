package com.dingrpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.dingrpc.RpcApplication;
import com.dingrpc.config.RpcConfig;
import com.dingrpc.constant.RpcConstant;
import com.dingrpc.fault.retry.RetryStrategy;
import com.dingrpc.fault.retry.RetryStrategyFactory;
import com.dingrpc.fault.tolerant.TolerantStrategy;
import com.dingrpc.fault.tolerant.TolerantStrategyFactory;
import com.dingrpc.model.RpcRequest;
import com.dingrpc.model.RpcResponse;
import com.dingrpc.model.ServiceMetaInfo;
import com.dingrpc.registry.Registry;
import com.dingrpc.registry.RegistryFactory;
import com.dingrpc.serializer.JdkSerializer;
import com.dingrpc.serializer.Serializer;
import com.dingrpc.serializer.SerializerFactory;
import com.dingrpc.server.tcp.VertxTcpClient;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 服务代理（JDK 动态代理）
 *
 * @author ding

 */
public class ServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器  工厂
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());
        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
            // 序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            // 从注册中心 获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());    //服务注册
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());  //服务发现
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }
            //先取一个
            ServiceMetaInfo selectedServiceMetaInfo = serviceMetaInfoList.get(0);

            //rpc 请求 起始
            //发送TCP请求
            //重试机制
            RpcResponse rpcResponse ;  //使用重试机制
            try {
                RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
                rpcResponse = retryStrategy.doRetry(()->
                        VertxTcpClient.doRequest(rpcRequest,selectedServiceMetaInfo));
            } catch (Exception e) {
                //容错机制
//                throw new RuntimeException(e);
                TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
                rpcResponse = tolerantStrategy.doTolerant(null, e);
            }
            return rpcResponse.getData();
          }
        catch (Exception e){
            throw  new RuntimeException("调用失败");
        }


        //   发送Http请求   使用注册中心和服务发现机制解决
      /*      try (HttpResponse httpResponse = HttpRequest.post(selectedServiceMetaInfo.getServiceAddress())
                    .body(bodyBytes)
                    .execute()) {
                byte[] result = httpResponse.bodyBytes();
                // 反序列化
                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
                return rpcResponse.getData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;*/



    }


}
