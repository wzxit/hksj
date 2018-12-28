package com.hksj.limit.demo.demo01.service;

import com.hksj.limit.demo.demo01.aop.MyAnnotation1;


@MyAnnotation1(name = "MyAnnotation1", label = "MyAnnotation1", description = "MyAnnotation1")
public interface MyService1 {
    void doA(String id);

    void doB(String id);
}