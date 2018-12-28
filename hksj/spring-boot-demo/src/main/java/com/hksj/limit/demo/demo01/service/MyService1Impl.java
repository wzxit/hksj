package com.hksj.limit.demo.demo01.service;

import org.springframework.stereotype.Service;

@Service("myService1Impl")
public class MyService1Impl implements MyService1 {
    @Override
    public void doA(String id) {
        System.out.println(id);
    }

    @Override
    public void doB(String id) {
        System.out.println(id);
    }
}