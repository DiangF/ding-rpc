package com.dingrpc.fault.tolerant;

import com.dingrpc.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
/**
 *
 *
 * 静默处理异常   --- 容错策略  （忽略异常）  不做处理 就当错误没有发生
 * @author  ding
 */


@Slf4j
public class FailSafeTolerantStrategy implements  TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        log.info("静默异常处理",e);
        return new RpcResponse();
    }
}
