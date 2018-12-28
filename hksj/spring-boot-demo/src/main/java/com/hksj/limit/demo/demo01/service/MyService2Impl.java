package com.hksj.limit.demo.demo01.service;

import com.hksj.limit.demo.demo01.aop.MyAnnotation2;
import org.springframework.stereotype.Service;

@Service("myService2Impl")
public class MyService2Impl {

    @MyAnnotation2(name = "MyAnnotation2",label = "MyAnnotation2",description = "MyAnnotation2")
    public void doA(String id) {
        System.out.println(id);
    }


    public void doB(String id) {
        System.out.println(id);
    }
}
