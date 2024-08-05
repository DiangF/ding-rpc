package com.dingrpc.registry;


import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.kv.GetResponse;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 *
 * Etcd  注册中心
 * @author  ding
 *
 */
public class EtcdRegisrtyDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建一个客户端端口
        Client clint = Client.builder().endpoints("http://localhost:2379")
                .build();
        KV kvClient = clint.getKVClient();

        ByteSequence key = ByteSequence.from("test_key".getBytes());
        ByteSequence value = ByteSequence.from("test_value".getBytes());

        //装入一个 键值
        kvClient.put(key,value).get();
//            创建线程池
        CompletableFuture<GetResponse> getFuture  = kvClient.get(key);

        // 获取值从 线程池
        GetResponse response = getFuture.get();
        //删除键

        kvClient.delete(key).get();


    }

}
