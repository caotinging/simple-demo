package com.caotinging.ymldemo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @program: simple-demo
 * @description: 映射Org属性
 * @author: CaoTing
 * @date: 2019/6/6
 **/
@Data
@ConfigurationProperties
public class OrgProperties {

    private List<String> orgs;
}
