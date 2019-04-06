package com.jiahangchun.autowire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyClass {
    @Autowired
    private MyBean myBean;
}
