package com.ding.service;

import com.ding.common.model.User;

/**
 *
 * 用户服务
 * @author  ding
 */
public interface UserService {
    /**
     *  获取用户的方法
     * @param user
     * @return
     */
    User getUser(User user);

    /**
     *   新方法  - 获取数字
     * @return
     */
    default  short getNumber(){
        return 1;
    }





}
