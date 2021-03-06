package com.hksj.limit.demo;

import com.hksj.limit.annotation.EnableLimit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@EnableLimit
@SpringBootApplication
public class LimitApplication {
    public static void main(String [] args){
        SpringApplication.run(LimitApplication.class, args);
    }
}
