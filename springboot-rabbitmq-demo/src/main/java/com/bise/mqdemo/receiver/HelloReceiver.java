package com.bise.mqdemo.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @desc: <p>
 *     经测试sender发送消息的时候这里三个handler是均匀的接收的，（但不是每条message都能让每个handler接收到
 * </p>
 * @program: simple-demo
 * @description: RabbitMQ入门-消息接收者
 * @author: CaoTing
 * @date: 2020/4/7
 */
@Component
public class HelloReceiver {

    @RabbitHandler
    @RabbitListener(queues = {"helloMQ"})
    public void process1(String hello) {
        System.out.println("Receiver1  : " + hello);
    }

    @RabbitHandler
    @RabbitListener(queues = {"helloMQ"})
    public void process2(String hello) {
        System.out.println("Receiver2  : " + hello);
    }

    @RabbitHandler
    @RabbitListener(queues = {"helloMQ"})
    public void process3(String hello) {
        System.out.println("Receiver3  : " + hello);
    }

    @RabbitHandler
    @RabbitListener(queues = "TopicHelloA")
    public void receiverTopicA(String message) {
        System.out.println("TopicHelloA :" + message);
    }

    @RabbitHandler
    @RabbitListener(queues = {"TopicHelloB","TopicHelloC"})
    public void receiverTopicBC(String message) {
        System.out.println("TopicHello [BC]: " + message);
    }

    @RabbitHandler
    @RabbitListener(queues = "TopicHelloD")
    public void receiverTopicD(String message) {
        System.out.println("TopicHelloD : "+message);
    }
}
