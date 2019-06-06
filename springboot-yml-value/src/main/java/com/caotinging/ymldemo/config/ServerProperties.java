package com.caotinging.ymldemo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: simple-demo
 * @description: 映射Server属性
 * @author: CaoTing
 * @date: 2019/6/3
 **/
@Data
@ConfigurationProperties("server")
public class ServerProperties {
 
    private String url;
 
    private final App app = new App();
 
    public App getApp() {
        return app;
    }
 
    public static class App {
 
        private String name;
        private String threadCount;
        private List<String> users = new ArrayList<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getThreadCount() {
            return threadCount;
        }

        public void setThreadCount(String threadCount) {
            this.threadCount = threadCount;
        }

        public List<String> getUsers() {
            return users;
        }

        public void setUsers(List<String> users) {
            this.users = users;
        }
    }
    
}
