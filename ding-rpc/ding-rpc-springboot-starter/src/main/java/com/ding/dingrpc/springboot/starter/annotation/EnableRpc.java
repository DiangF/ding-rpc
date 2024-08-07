package com.ding.dingrpc.springboot.starter.annotation;


import com.ding.dingrpc.springboot.starter.bootstrap.RpcConsumerBootstrap;
import com.ding.dingrpc.springboot.starter.bootstrap.RpcInitBootstrap;
import com.ding.dingrpc.springboot.starter.bootstrap.RpcProviderBootstrap;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  启用RPC 注解
 * @author  ding
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)   //告诉编译器这个注解应该在运行时保留，并且可以通过反射API来访问
@Import({RpcInitBootstrap.class, RpcProviderBootstrap.class, RpcConsumerBootstrap.class})
public @interface  EnableRpc {

    /**
     * 需要驱动 server
     * @return
     */
    boolean needServer() default  true;

}
