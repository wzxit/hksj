package com.hksj.limit.demo.demo01;

import com.hksj.limit.demo.demo01.service.MyService1;
import com.hksj.limit.demo.demo01.service.MyService2Impl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TestApplication02 {
    public static void main(String [] args){
        ConfigurableApplicationContext applicationContext = SpringApplication.run(TestApplication02.class, args);

        MyService2Impl myService1 = applicationContext.getBean(MyService2Impl.class);
        myService1.doA("C");
        myService1.doB("D");
    }
}
