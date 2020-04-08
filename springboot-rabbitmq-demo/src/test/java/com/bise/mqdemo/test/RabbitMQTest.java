package com.bise.mqdemo.test;

import com.bise.mqdemo.sender.bootstrap.SenderApplication;
import com.bise.mqdemo.sender.sender.MultiSimpleSender;
import com.bise.mqdemo.sender.sender.SimpleSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: bise
 * @Date: 2019/6/14 15:21
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SenderApplication.class)
public class RabbitMQTest {

    @Autowired
    private SimpleSender simpleSender;

    @Autowired
    private MultiSimpleSender multiSimpleSender;

    /**
     * RabbitMQ入门
     * 测试简单的默认交换机 Direct Exchange
     * 路由模式为 1:1 完全匹配模式
     * @throws Exception
     */
    @Test
    public void hello() throws Exception {
        for (int i = 0; i < 10; i++) {
            simpleSender.send();
            Thread.sleep(1000);
        }
    }

    /**
     * 默认交换机 测试多对多发送
     * @throws Exception
     */
    @Test
    public void helloMulti() throws Exception {
        for (int i = 0; i < 10; i++) {
            simpleSender.send();
            simpleSender.send("HELLO2");
            multiSimpleSender.send("MULTI_HELLO");
            Thread.sleep(1000);
        }
    }
}
