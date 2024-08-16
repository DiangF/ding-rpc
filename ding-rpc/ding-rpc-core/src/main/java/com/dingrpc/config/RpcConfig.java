package com.dingrpc.config;



import com.dingrpc.fault.retry.RetryStrategyKeys;
import com.dingrpc.fault.tolerant.TolerantStrategyFactory;
import com.dingrpc.fault.tolerant.TolerantStrategyKeys;
import com.dingrpc.loadbalancer.LoadBalancerKeys;
import com.dingrpc.serializer.SerializerKeys;
import lombok.Data;

/**
 * @Time
 * @author ding
 * RPC 配置框架
 */

@Data
public class RpcConfig {

    /**
     * 名称
     */
    private  String name = "ding-rpc";
    /**
     * 版本号
     */
    private  String version = "1.0";
    /**
     * 服务器主机名
     */
    private String serverHost  = "localhost";
    /**
     * 服务器端口号
     */
    private  Integer serverPort  = 8080;

    /**
     * 模拟调用
     */
    private boolean mock = false;

    /**
     * 序列化器
     */
    private  String serializer = SerializerKeys.JDK;

    /**
     * 注册中心配置
     */
    private RegistryConfig registryConfig = new RegistryConfig();
    /**
     *重试策略
     *
     */
    private String retryStrategy = RetryStrategyKeys.NO;
    /**
     * 容错策略
     */
    private String tolerantStrategy = TolerantStrategyKeys.FAIL_FAST;   //默认快速失败

    /**
     *   负载均衡器
     */
    private String loadBalancer = LoadBalancerKeys.ROUND_ROBIN;  //默认轮询

}
