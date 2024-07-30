package com.ding.common.model;

import java.io.Serializable;


//通过将对象序列化，可以让对象用于网络传输和永久化存储   实现序列化接口，为网络传输序列化提供支持
public class User implements Serializable {
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
