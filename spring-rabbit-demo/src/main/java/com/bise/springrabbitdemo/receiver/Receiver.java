package com.bise.springrabbitdemo.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: bise
 * @Date: 2019/6/14 10:27
 * @Version 1.0
 */
@Component
public class Receiver {

    @RabbitListener(queues = "hello.D")
    public void processA(String hello) {
        System.out.println("Receiver 1: " + hello);
    }

    @RabbitListener(queues = "hello.C")
    public void processB(String hello) {
        System.out.println("Receiver 2: " + hello);
    }

}
