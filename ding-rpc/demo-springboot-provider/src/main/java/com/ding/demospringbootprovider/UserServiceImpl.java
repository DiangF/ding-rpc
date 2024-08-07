package com.ding.demospringbootprovider;

import com.ding.common.model.User;
import com.ding.service.UserService;
import com.ding.dingrpc.springboot.starter.annotation.RpcService;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 *
 * @author ding
 */
@Service
@RpcService
public class UserServiceImpl implements UserService {
    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
