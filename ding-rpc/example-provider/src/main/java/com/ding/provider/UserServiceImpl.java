package com.ding.provider;

import com.ding.common.model.User;
import com.ding.service.UserService;

public class UserServiceImpl implements UserService {
    /**
     *
     * 用户实现服务类
     *
     * @param user
     * @return
     */
    @Override
    public User getUser(User user) {
        System.out.println("用户名："+ user.getName());
        return user;
    }



}
