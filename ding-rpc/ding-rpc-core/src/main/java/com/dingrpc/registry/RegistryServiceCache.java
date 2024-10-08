package com.dingrpc.registry;


import com.dingrpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * 注册中心本地缓存
 */
public class RegistryServiceCache {

    /**
     *
     * 服务缓存
     */
    List<ServiceMetaInfo> serviceCache;

    /**
     * 写缓存
     * @param  newServiceCache
     */
    void writeCache(List<ServiceMetaInfo> newServiceCache){
        this.serviceCache = newServiceCache;
    }


    /**
     * 读缓存
     */

    List<ServiceMetaInfo> readCache(String serviceKey) {
        return this.serviceCache;
    }

    /*
     *  清空缓存
     */
    void clearCache(){
        this.serviceCache = null;
    }


}
