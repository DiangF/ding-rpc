package com.dingrpc.model;


import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * 存放服务的元信息（注册信息）
 * @author  ding
 */

@Data
public class ServiceMetaInfo {

    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 服务版本号
     */
    private String serviceVersion = "1.0";
    /**
     * 服务域名
     */
    private String serviceHost;
    /**
     * 服务端口号
     */
    private Integer servicePort;
    /**
     * 服务分组（TODO）
     */
    private String serviceGroup = "default";



    /*增加一些工具方法  获取服务键名 和注册节点名称*/

    /**
     * 获取服务键名
     * @return
     */
    public String getServiceKey(){
     return String.format("%s:%s",serviceName,serviceVersion);
    }

    /**
     *   获取服务注册节点键名称
     * @return
     */
    public String getServiceNodeKey(){
        return String.format("%s/%s:%s",getServiceKey(),serviceHost,servicePort);
    }

    public String getServiceAddress(){
        if(!StrUtil.contains(serviceHost,"http")){
            return String.format("http://%s:%s",serviceHost,servicePort);
        }
        return String.format("%s:%s",serviceHost,servicePort);
    }
}
