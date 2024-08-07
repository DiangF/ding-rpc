package com.ding;

import com.ding.common.model.User;
import com.ding.service.UserService;
import com.dingrpc.bootstrap.ConsumerBootstrap;
import com.dingrpc.config.RpcConfig;
import com.dingrpc.proxy.ServiceProxyFactory;
import com.dingrpc.utils.ConfigUtils;

/**
 * 消费者类 简易版扩展
 * @author ding
 */
public class ConsumerExample {
    public static void main(String[] args) {

        ConsumerBootstrap.init();
        //获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("dingzf");
        //调用
        User newUser = userService.getUser(user);
        if(newUser != null){
            System.out.println(newUser.getName());
        }else{
            System.out.println("user == null");
        }
//        short number = userService.getNumber();
//        System.out.println(number);

    }

}
