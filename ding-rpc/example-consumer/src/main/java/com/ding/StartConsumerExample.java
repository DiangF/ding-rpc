/*
package com.ding;
import com.ding.common.model.User;
import com.ding.ding.dingrpc.proxy.ServiceProxyFactory;
import com.ding.service.UserService;

public class StartConsumerExample {
    */
/**
     *
     *
     * 消费者示例
     * @param args
     * @author ding
     *//*


    public static void main(String[] args) {
//        UserService userService = new UserServiceProxy();  //静态代理
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

    }
}
*/
