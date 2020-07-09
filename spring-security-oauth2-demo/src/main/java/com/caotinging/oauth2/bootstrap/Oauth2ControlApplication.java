package com.caotinging.oauth2.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @program: bank-door-control
 * @description: 门禁管理平台启动类
 * @author: CaoTing
 * @date: 2020/6/12
 */
@SpringBootApplication(scanBasePackages = "com.caotinging.oauth2.**")
public class Oauth2ControlApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Oauth2ControlApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Oauth2ControlApplication.class, args);
    }

}
