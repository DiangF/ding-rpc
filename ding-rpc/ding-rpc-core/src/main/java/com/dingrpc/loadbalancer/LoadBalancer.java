package com.dingrpc.loadbalancer;

import com.dingrpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * 负载均衡器（消费者使用）
 * @author  ding
 */
public interface LoadBalancer {

    /**
     * 选择服务进行调用
     *
     * @param requestParams  请求参数
     * @param serviceMetaInfoList  可用服务列表
     * @return
     */
    ServiceMetaInfo select(Map<String,Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList);
}
