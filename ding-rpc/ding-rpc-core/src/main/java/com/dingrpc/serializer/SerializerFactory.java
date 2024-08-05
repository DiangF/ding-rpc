package com.dingrpc.serializer;

import com.dingrpc.spi.SpiLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * 序列化工厂
 * @author  ding
 */
public class SerializerFactory {
   /* private static final Map<String,Serializer> KEY_SERIALIZER_MAP = new HashMap<String,Serializer>(){{
        put(SerializerKeys.JDK,new JdkSerializer());
        put(SerializerKeys.JSON,new JsonSerializer());
        put(SerializerKeys.KRYO,new KryoSerializer());
        put(SerializerKeys.HESSIAN,new HessianSerializer());

    }};
*/
    /**
     *
     * 通过SPI 方式获取序列化器
     */
    static {
        SpiLoader.load(Serializer.class);
    }

    /**
     *
     * 默认序列化器
     */
//    private static final Serializer DEFAULT_SERIALIZER = KEY_SERIALIZER_MAP.get("jdk");
    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();
    /**
     * 存储加载后的序列化器
     */
    private static volatile Map<String,Serializer> serializerMap;

    /**
     * 获取实例
     * @param key
     * @return
     */
    public static Serializer getInstance(String key){
//           return KEY_SERIALIZER_MAP.getOrDefault(key,DEFAULT_SERIALIZER);   //HashMap读取 序列化器实现类
        //懒汉式加载 SPI 序列化器
        if(serializerMap == null){
            synchronized (SerializerFactory.class){
                if(serializerMap==null){
                    SpiLoader.load(Serializer.class);
                    serializerMap = new HashMap<>();
                }
            }
        }
        return SpiLoader.getInstance(Serializer.class,key);   //SPI 加载指定序列化器
    }


}
