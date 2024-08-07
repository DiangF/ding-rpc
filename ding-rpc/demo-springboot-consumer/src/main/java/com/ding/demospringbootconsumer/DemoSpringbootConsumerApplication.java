package com.ding.demospringbootconsumer;

import com.ding.dingrpc.springboot.starter.annotation.EnableRpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRpc(needServer = false)
public class DemoSpringbootConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringbootConsumerApplication.class, args);
    }

}
