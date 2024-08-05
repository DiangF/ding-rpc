package com.ding;

import com.ding.common.model.User;
import com.ding.service.UserService;
import com.dingrpc.config.RpcConfig;
import com.dingrpc.proxy.ServiceProxyFactory;
import com.dingrpc.utils.ConfigUtils;

/**
 * 消费者类 简易版扩展
 * @author ding
 */
public class ConsumerExample {
    public static void main(String[] args) {
            //测试全局配置是否加载成功
       /* RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class,"rpc");
        System.out.println(rpc);*/
//        UserService userService = new UserServiceProxy();   //静态代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("ding");
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
