package com.bise.springrabbitdemo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: bise
 * @Date: 2019/6/14 10:28
 * @Version 1.0
 */
@Configuration
public class RabbitConfig {
/*
    @Bean
    public Queue helloAQueue() {
        return new Queue("hello.A");
    }

    @Bean
    public Queue helloBQueue() {
        return new Queue("hello.B");
    }

    @Bean
   TopicExchange helloExchange() {
        return new TopicExchange("helloExchange");
    }

    @Bean
    Binding bindingExchangeA() {
        return BindingBuilder.bind(helloAQueue()).to(helloExchange()).with("aaa.A");
    }*/

/*    @Bean
    Binding bindingExchangeB() {
        return BindingBuilder.bind(helloBQueue()).to(helloExchange()).with("hello.bbc.#");
    }*/


}
