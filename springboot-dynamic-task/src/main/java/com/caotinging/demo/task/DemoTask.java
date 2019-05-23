package com.caotinging.demo.task;

import org.springframework.stereotype.Component;

/**
 * @program: simple-demo
 * @description:
 * @author: CaoTing
 * @date: 2019/5/23
 **/
@Component("demoTask")
public class DemoTask {

    public void taskWithParams(String params) {
        System.out.println("执行有参示例任务：" + params);
    }

    public void taskNoParams() {
        System.out.println("执行无参示例任务");
    }
}
