package com.bise.mqdemo.sender;

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
public class TopicSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendA() {
        String context = "helloA " + new Date();
        this.rabbitTemplate.convertAndSend("TopicHelloExchange","TopicHello.A.A.A", context);
    }

    public void sendB() {
        String context = "helloB " + new Date();
        this.rabbitTemplate.convertAndSend("TopicHelloExchange" ,"B.BB.BBB.TopicHello" , context);
    }

    public void sendC() {
        String context = "helloC " + new Date();
        this.rabbitTemplate.convertAndSend("TopicHelloExchange","TopicHello.C" , context);
    }
}
