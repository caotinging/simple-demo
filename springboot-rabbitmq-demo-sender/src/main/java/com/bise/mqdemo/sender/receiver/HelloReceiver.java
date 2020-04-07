package com.bise.mqdemo.sender.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @program: simple-demo
 * @description: RabbitMQ入门-消息接收者
 * @author: CaoTing
 * @date: 2020/4/7
 */
@Component
@RabbitListener(queues = {"helloMQ"})
public class HelloReceiver {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver  : " + hello);
    }
}
