package com.ding.provider;
import com.ding.service.UserService;
import com.dingrpc.RpcApplication;
import com.dingrpc.bootstrap.ProviderBootstrap;
import com.dingrpc.config.RegistryConfig;
import com.dingrpc.config.RpcConfig;
import com.dingrpc.model.ServiceMetaInfo;
import com.dingrpc.model.ServiceRegisterInfo;
import com.dingrpc.registry.LocalRegistry;
import com.dingrpc.registry.Registry;
import com.dingrpc.registry.RegistryFactory;
import com.dingrpc.server.HttpServer;
import com.dingrpc.server.VertexHttpServer;
import com.dingrpc.server.tcp.VertxTcpServer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 提供者 类
 * @author ding
 */

public class ProviderExample {
    public static void main(String[] args)  {

        //注册的服务
        List<ServiceRegisterInfo<?>> serviceRegisterInfoList = new ArrayList<>();
        ServiceRegisterInfo<UserService> serviceRegisterInfo = new ServiceRegisterInfo<>(UserService.class.getName(),UserServiceImpl.class);
        serviceRegisterInfoList.add(serviceRegisterInfo);
        //  服务提供者初始化
        ProviderBootstrap.init(serviceRegisterInfoList);




    }

}
