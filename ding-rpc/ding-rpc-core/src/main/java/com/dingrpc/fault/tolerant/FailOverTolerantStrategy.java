package com.dingrpc.fault.tolerant;

import com.dingrpc.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 转移到其他服务节点 - 容错策略
 *
 * @author ding

 */
@Slf4j
public class FailOverTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        // TODO 待扩展
        return null;
    }
}
