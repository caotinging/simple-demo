package com.caotinging.ymldemo.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @program: simple-demo
 * @description: 注册所有映射属性类  { }中用逗号分隔即可注册多个属性类
 * @author: CaoTing
 * @date: 2019/6/3
 **/
@Configuration
@EnableConfigurationProperties({ServerProperties.class,OrgProperties.class})
public class ApplicationConfig {
 
}
