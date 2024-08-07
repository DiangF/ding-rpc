package com.ding.demospringbootconsumer;

import com.ding.common.model.User;
import com.ding.service.UserService;
import com.ding.dingrpc.springboot.starter.annotation.RpcReference;
import org.springframework.stereotype.Service;

/**
 * 示例服务实现类
 *
 * @author ding
 */
@Service
public class DemoServiceImpl {

    /**
     * 使用 Rpc 框架注入
     */
    @RpcReference
    private UserService userService;

    /**
     * 测试方法
     */
    public void test() {
        User user = new User();
        user.setName("ding");
        User resultUser = userService.getUser(user);
        System.out.println(resultUser.getName());
    }

}
