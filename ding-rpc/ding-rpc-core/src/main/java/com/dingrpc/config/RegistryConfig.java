package com.dingrpc.config;


import lombok.Data;

/**
 * RPC 框架注册中心配置
 */

@Data
public class RegistryConfig
{
    /**
     * 注册中心列类别
     */
     private String registry = "etcd";
    /**
     * 注册中心地址
     */
    private String address = "http://localhost:2380";
    /**
     * 用户名
     */
     private String username ;
    /**
     * 密码
     */
    private String password ;
    /**
     * 超时时间（ms）
     */
     private Long  timeout = 100000L;

}
