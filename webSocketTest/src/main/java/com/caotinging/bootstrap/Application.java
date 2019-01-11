package com.caotinging.bootstrap;

/**
 * @author caoting
 * @date 2018年9月19日
 */
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import static org.springframework.boot.SpringApplication.run;

@ComponentScan(basePackages ="com.caotinging")
@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = run(Application.class, args);
    }
}