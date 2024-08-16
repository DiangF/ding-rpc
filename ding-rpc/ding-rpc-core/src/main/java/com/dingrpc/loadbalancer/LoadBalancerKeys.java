package com.dingrpc.loadbalancer;

/**
 * 负载均衡器键名常量
 *
 * @author ding
 */
public interface LoadBalancerKeys {

    /**
     * 轮询方法
     */
    String ROUND_ROBIN = "roundRobin";

    /**
     * 随机算法
     */
    String RANDOM = "random";
    /**
     * 一致性hash
     */
    String CONSISTENT_HASH = "consistentHash";

}
