package com.ding.demospringbootconsumer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class DemoSpringbootConsumerApplicationTests {
    @Resource
    private DemoServiceImpl demoService;


    @Test
    void contextLoads() {
    }
    @Test
    void test1(){
        demoService.test();
    }

}
