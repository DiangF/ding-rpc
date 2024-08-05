package com.dingrpc.proxy;


import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 *  Mock 服务代理（JDK动态代理）
 * @author ding
 *
 */

@Slf4j
public class MockServiceProxy  implements InvocationHandler {
    /**
     *
     *调用代理
     *
     * @return
     * @throws Throwable
     */

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 根据方法返回值类型，生成特定的默认值对象
        Class<?> methodReturnType = method.getReturnType();
        log.info("Mock invoke {}",method.getName());
        //针对代理接口的class 返回不同的默认值
        return getDefaultObject(methodReturnType);
    }

    /**
     * 生成指定类型的默认值对象
     * @param methodReturnType
     * @return
     */

    private Object getDefaultObject(Class<?> methodReturnType) {
        //确定指定的Class对象是基本类型
        if(methodReturnType.isPrimitive()){
            if(methodReturnType == boolean.class){
                return false;
            }else if(methodReturnType == short.class){
                return (short)0;
            }else if(methodReturnType == int.class){
                   return 0;
            }else if(methodReturnType == long.class){
                return 0L;
            }
        }
        //是对象类型
        return null;
    }
}
