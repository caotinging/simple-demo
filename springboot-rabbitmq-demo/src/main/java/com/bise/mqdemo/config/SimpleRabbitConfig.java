package com.bise.mqdemo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: simple-demo
 * @description: RabbitMQ入门 默认的Direct交换机
 * @author: CaoTing
 * @date: 2020/4/7
 */
@Configuration
public class SimpleRabbitConfig {

    // 设置一个简单的队列
    @Bean
    public Queue queue() {
        return new Queue("helloMQ");
    }

}
