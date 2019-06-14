package com.bise.springrabbitdemo.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: bise
 * @Date: 2019/6/14 15:55
 * @Version 1.0
 */
@Component
public class FanoutSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(int i) {
        String context = "hello " + i;
        System.out.println("Sender : " + context);
        rabbitTemplate.convertAndSend("fanoutExchange","11", context);
    }
}
