package com.bise.mqdemo.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: bise
 * @Date: 2019/6/14 10:27
 * @Version 1.0
 */
@Component
public class TopicReceiver {

    @RabbitListener(queues = "TopicHelloA")
    public void processTopicA(String hello) {
        System.out.println("TopicReceiver 'TopicHello.A': " + hello);
    }

    @RabbitListener(queues = "TopicHelloB")
    public void processTopicB(String hello) {
        System.out.println("TopicReceiver '#.TopicHello': " + hello);
    }

    @RabbitListener(queues = "TopicHelloC")
    public void processTopicC(String hello) {
        System.out.println("TopicReceiver 'TopicHello.*': " + hello);
    }

    @RabbitListener(queues = "TopicHelloD")
    public void processTopicD(String hello) {
        System.out.println("TopicReceiver '#': " + hello);
    }

}
