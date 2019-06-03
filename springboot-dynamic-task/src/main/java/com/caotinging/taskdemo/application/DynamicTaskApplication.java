package com.caotinging.taskdemo.application;

import org.mybatis.spring.annotation.MapperScan;
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
@ComponentScan("com.caotinging.taskdemo.**")
@MapperScan("com.caotinging.taskdemo.dao.**")
public class DynamicTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicTaskApplication.class, args);
    }
}
