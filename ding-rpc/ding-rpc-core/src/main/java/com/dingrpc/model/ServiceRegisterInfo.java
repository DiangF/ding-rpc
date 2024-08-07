package com.dingrpc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 服务注册信息类
 * @author  ding
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRegisterInfo <T>{

    /**
     * 服务名称
     */

    private String serviceName;

    /**
     * 实现类
     */
    private  Class<? extends T> implClass;



}