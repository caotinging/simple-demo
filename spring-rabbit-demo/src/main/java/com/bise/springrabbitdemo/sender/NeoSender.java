package com.bise.springrabbitdemo.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: bise
 * @Date: 2019/6/14 10:26
 * @Version 1.0
 */
@Component
public class NeoSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(int i) {
        String context = "hello " + i;
        System.out.println("NeoSender : " + context);
        this.rabbitTemplate.convertAndSend("helloExchange1","aaa.A", context);
    }



}
