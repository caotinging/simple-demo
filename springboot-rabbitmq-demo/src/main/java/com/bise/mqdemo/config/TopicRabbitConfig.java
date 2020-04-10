package com.bise.mqdemo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Desc: Topic交换机 队列
 * @Author: bise
 * @Date: 2019/6/14 10:28
 * @Version 1.0
 */
@Configuration
public class TopicRabbitConfig {

    /**
     * 注册队列（同时自动在Rabbit服务器创建队列）
     * @return
     */
    @Bean
    public Queue helloQueueA() {
        return new Queue("TopicHelloA" ,true);
    }

    @Bean
    public Queue helloQueueB() {
        return new Queue("TopicHelloB" ,true);
    }

    @Bean
    public Queue helloQueueC() {
        return new Queue("TopicHelloC" ,true);
    }

    @Bean
    public Queue helloQueueD() {
        return new Queue("TopicHelloD" ,true);
    }


   /**
    * 创建交换机
    * @return
   */
   @Bean
   TopicExchange helloExchange() {
        return new TopicExchange("TopicHelloExchange" ,true ,false);
    }

    /**
     * 绑定队列与交换机
     * routingKey #匹配一个或多个字符，*匹配一个字符
     * @return
     */
    @Bean
    Binding topicBindingExchangeA() {
        /*
        * TopicHello.A.A.A 必须要routingKey完全一致才能收到
        * */
        return BindingBuilder.bind(helloQueueA()).to(helloExchange()).with("TopicHello.A.A.A");
    }
    @Bean
    Binding topicBindingExchangeB() {
        /*
         * #.TopicHello ‘#’可以为任意个单词，单词以‘.’来分割
         * */
        return BindingBuilder.bind(helloQueueB()).to(helloExchange()).with("#.TopicHello");
    }
    @Bean
    Binding topicBindingExchangeC() {
        /*
         * TopicHello.* ‘*’为一个单词，单词以‘.’来分割
         * */
        return BindingBuilder.bind(helloQueueC()).to(helloExchange()).with("TopicHello.*");
    }
    @Bean
    Binding topicBindingExchangeD() {
        /*
         * # 单独一个‘#’代表任何消息都能接收
         * */
        return BindingBuilder.bind(helloQueueD()).to(helloExchange()).with("#.A.Topic");
    }

}
