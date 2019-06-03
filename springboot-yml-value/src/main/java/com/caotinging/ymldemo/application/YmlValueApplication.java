package com.caotinging.ymldemo.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @program: simple-demo
 * @description: 定时任务动态增改入口类
 *
 * @author: CaoTing
 * @date: 2019/5/22
 **/
@SpringBootApplication
@ComponentScan("com.caotinging.ymldemo.**")
public class YmlValueApplication {

    public static void main(String[] args) {
        SpringApplication.run(YmlValueApplication.class, args);
    }
}
