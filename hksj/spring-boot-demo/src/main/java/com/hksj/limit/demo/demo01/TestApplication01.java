package com.hksj.limit.demo.demo01;

import com.hksj.limit.demo.demo01.service.MyService1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TestApplication01 {
    public static void main(String [] args){
        ConfigurableApplicationContext applicationContext = SpringApplication.run(TestApplication01.class, args);

        MyService1 myService1 = applicationContext.getBean(MyService1.class);
        myService1.doA("A");
        myService1.doB("B");
    }
}
