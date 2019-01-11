package com.caotinging.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.caotinging.https.RestTemplateFactory;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RestApplicationRunner implements ApplicationRunner {

    @Autowired
    private RestTemplateFactory restTemplateFactory;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            restTemplateFactory.init();
            log.info("HTTPS连接池加载成功!");
        } catch (Exception e) {
            log.error("HTTPS连接池加载失败!");
        }

    }
}
