package com.ding.provider;
import com.ding.service.UserService;
import com.dingrpc.RpcApplication;
import com.dingrpc.config.RegistryConfig;
import com.dingrpc.config.RpcConfig;
import com.dingrpc.model.ServiceMetaInfo;
import com.dingrpc.registry.LocalRegistry;
import com.dingrpc.registry.Registry;
import com.dingrpc.registry.RegistryFactory;
import com.dingrpc.server.HttpServer;
import com.dingrpc.server.VertexHttpServer;

/**
 *
 * 提供者 类
 * @author ding
 */

public class ProviderExample {
    public static void main(String[] args)  {
        // RPC 框架初始化
        RpcApplication.init();
        //注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);
        //注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //启动web服务
        HttpServer httpServer = new VertexHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());



    }

}
