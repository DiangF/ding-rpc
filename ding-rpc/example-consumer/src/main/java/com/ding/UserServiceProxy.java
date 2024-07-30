package com.ding;
import com.ding.common.model.User;
import com.ding.ding.dingrpc.model.RpcRequest;
import com.ding.ding.dingrpc.model.RpcResponse;
import com.ding.ding.dingrpc.serializer.JdkSerializer;
import com.ding.ding.dingrpc.serializer.Serializer;
import com.ding.service.UserService;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import java.io.IOException;


/*
静态代理
 */
public class UserServiceProxy implements UserService {
    @Override
    public User getUser(User user) {
        //指定序列化器
        Serializer serializer = new JdkSerializer();
        //发请求

        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(UserService.class.getName())
                .methodName("getUser")
                .parameterTypes(new Class[]{User.class})
                .args(new Object[]{user})
                .build();
        try {
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            byte[] result;
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes)
                    .execute()) {
                result = httpResponse.bodyBytes();
            }
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return (User) rpcResponse.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



}
