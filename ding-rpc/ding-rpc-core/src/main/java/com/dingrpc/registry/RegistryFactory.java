package com.dingrpc.registry;


import com.dingrpc.spi.SpiLoader;

/**
 * 注册中心 工厂（用于获取注册中心对象）
 * @author ding
 */
public class RegistryFactory {


    //静态代码块 类加载时执行 只执行一次
    static {
        SpiLoader.load(Registry.class);

    }

    /**
     * 默认注册中心
     */

    private static final Registry DEFAULT_REGISTRY = new EtcdRegistry();

    /**
     * 获取实例
     * @return
     */
    public static Registry getInstance(String key){

        return SpiLoader.getInstance(Registry.class,key);
    }




}
