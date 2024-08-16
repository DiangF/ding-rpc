package com.dingrpc.loadbalancer;

import com.dingrpc.spi.SpiLoader;

/**
 *
 * 负载均衡器工厂 （工厂模式 创建负载器均衡对象）
 *
 */
public class LoadBalancerFactory {

    static{
        SpiLoader.load(LoadBalancer.class);
    }

    /**
     *默认负载均衡器
     */
    private static final LoadBalancer  DEFAULT_LOAD_BALANCER = new RoundRobinLoadBalancer();

    /**
     * 获取实例   SPI 机制
     * @param key
     * @return
     */
    public static LoadBalancer getInstance(String key){
        return SpiLoader.getInstance(LoadBalancer.class,key);
    }


}
