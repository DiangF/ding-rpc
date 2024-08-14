package com.dingrpc.fault.tolerant;


import com.dingrpc.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 *
 * 降级到其他服务  -容错策略
 * @author  ding <a href="https://github.com/DiangF/ding-rpc"><a/>
 */
@Slf4j
public class FailBackTolerantStrategy implements  TolerantStrategy{

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        //TODO  待扩展
        return null;
    }
}
