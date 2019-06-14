package com.bise.springrabbitdemo.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = {"com.bise.springrabbitdemo.**"})
public class MapServiceWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(MapServiceWebApplication.class, args);
    }
}
