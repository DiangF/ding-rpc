## **ding-rpc --- 轻量级RPC框架**  
一款基于ETCD+Zookeeper+Spring实现的轻量级Java RPC框架。提供服务注册，发现，API调用，Spring集成和Spring Boot starter驱动注解的使用。
类似与Dubbo服务端框架的模式，进行轻量级RPC框架开发。
## **RPC框架执行流程**  
简述整个项目的调用流程：
![](https://s3.bmp.ovh/imgs/2024/08/11/987b80668a424ae8.png)
**具体的调用过程概括如下：**  
1、服务消费者（client客户端）通过本地调用的方式调用服务  
2、客户端存根（client stub）接收到调用请求后负责将方法、入参等信息序列化（组装）成能够进行网络传输的消息体  
3、客户端存根（client stub）找到远程的服务地址，并且将消息通过网络发送给服务端  
4、服务端存根（server stub）收到消息后进行解码（反序列化操作）  
5、服务端存根（server stub）根据解码结果调用本地的服务进行相关处理（服务提供者）  
6、本地服务执行具体业务逻辑并将处理结果返回给服务端存根（server stub）  
7、服务端存根（server stub）将返回结果重新打包成消息（序列化）并通过网络发送至消费方  
8、客户端存根（client stub）接收到消息，并进行解码（反序列化）    
9、服务消费方得到最终结果
### 项目目录结构
ding-rpc框架
├─ding-rpc-core	--ding-rpc核心实现类  
├─ding-rpc-springboot-starter	--组件的spring-boot-starter接入类  
├─example-consumer	--服务消费者  
├─example-common	--示例代码公共模块  
└─example-provider	--服务提供者  
### 核心模块结构
├─ding-rpc-core  
│  ├─src  
│  │  ├─main  
│  │  │  ├─java  
│  │  │  │  └─com  
│  │  │  │      └─dingrpc  
│  │  │  │          ├─bootstrap                  - > 服务消费者、提供者启动类   
│  │  │  │          ├─config                     - > 项目配置（服务端、客户端属性配置）   
│  │  │  │          ├─constant                   - > 项目常量  
│  │  │  │          ├─fault                      - > 消费者调用接口异常   
│  │  │  │          │  └─retry                   - > 重试机制  
│  │  │  │          │  └─tolerant                - > 容错机制  
│  │  │  │          ├─loadbalancer               - > 负载均衡  
│  │  │  │          ├─model                      - > 封装一些返回公共信息   
│  │  │  │          ├─protocol                   - > 协议消息处理器 编码 解码  
│  │  │  │          ├─proxy                      - > 动态代理（JDK动态代理）  
│  │  │  │          ├─registry                   - > 注册中心  
│  │  │  │          ├─serializer                 - > 序列化器(可以自定义)  
│  │  │  │          ├─server                     - > 服务端相关类（请求处理、加载）  
│  │  │  │          │  └─tcp                     - > TCP协议对请求进行处理  
│  │  │  │          ├─spi                        - > SPI 定制加载类  
│  │  │  │          └─utils                      - > 项目工具包（读取相关配置文件，配合SPI机制实现）  

## RPC框架
![](https://s3.bmp.ovh/imgs/2024/08/08/d3ccb4d78012a8db.jpg)






