package com.bise.mqdemo.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: simple-demo
 * @description: RabbitMQ入门-消息发送者/生产者
 * @author: CaoTing
 * @date: 2020/4/7
 */
@Component
public class SimpleSender {

    @Autowired
    private AmqpTemplate rabbitMQTemplate;

    public void send() {
        String context = "helloMQ "+ new Date().getTime();
        rabbitMQTemplate.convertAndSend("helloMQ", context);
    }

    public void send(String name) {
        String context = "helloMQ "+ name +":" + new Date().getTime();
        rabbitMQTemplate.convertAndSend("helloMQ", context);
    }
}
