package com.caotinging.taskdemo.task;

import org.springframework.stereotype.Component;

/**
 * @program: simple-demo
 * @description:
 * @author: CaoTing
 * @date: 2019/5/23
 **/
@Component("demoTask")
public class DemoTask {

    public void taskWithParams(String param1, Integer param2) {
        System.out.println("这是有参示例任务：" + param1 + param2);
    }

    public void taskNoParams() {
        System.out.println("执行无参示例任务");
    }
}
