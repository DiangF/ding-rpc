package com.ding.provider;

import com.ding.ding.dingrpc.registry.LocalRegistry;
import com.ding.ding.dingrpc.server.HttpServer;
import com.ding.ding.dingrpc.server.VertexHttpServer;
import com.ding.service.UserService;


public class StartProvideExample {

    public static void main(String[] args) {
        //提供服务
        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
        // 启动 web 服务
        HttpServer httpServer = new VertexHttpServer();
        httpServer.doStart(8080);


    }
}
