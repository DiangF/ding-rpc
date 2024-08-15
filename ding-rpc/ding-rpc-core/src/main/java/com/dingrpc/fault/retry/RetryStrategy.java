package com.dingrpc.fault.retry;

import com.dingrpc.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 *
 *   重试策略
 * @author   ding
 */

public interface RetryStrategy {

    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;
}


