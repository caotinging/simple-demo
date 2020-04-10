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
public class RabbitReceiver {

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
    @RabbitListener(queues = {"TopicHelloB","TopicHelloD"})
    public void receiverTopicBC(Object message) {
        System.out.println("TopicHello [BD]: " + message);
        System.out.println("==");
    }

    @RabbitHandler
    @RabbitListener(queues = "fanout.A")
    public void receiverFanoutA(String message) {
        System.out.println("FanoutA :" + message);
    }

    @RabbitHandler
    @RabbitListener(queues = "fanout.B")
    public void receiverFanoutB(String message) {
        System.out.println("FanoutB :" + message);
    }

    @RabbitHandler
    @RabbitListener(queues = "TopicHelloC")
    public void receiverUser(User user) {
        System.out.println("TopicHelloC: " +user.toString());
    }
}
