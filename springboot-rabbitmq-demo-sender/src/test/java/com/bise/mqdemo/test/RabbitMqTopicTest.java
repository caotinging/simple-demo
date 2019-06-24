package com.bise.mqdemo.test;

import com.bise.mqdemo.sender.bootstrap.SenderApplication;
import com.bise.mqdemo.sender.FanoutSender;
import com.bise.mqdemo.sender.NeoSender;
import com.bise.mqdemo.sender.TopicSender;
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
public class RabbitMqTopicTest {


    @Autowired
    private TopicSender topicSender;

   @Test
    public void hello() throws Exception {
       topicSender.sendA();
       Thread.sleep(1000);
       topicSender.sendB();
       Thread.sleep(1000);
       topicSender.sendC();
       Thread.sleep(1000);
       topicSender.sendD();
    }

}
