package com.bise.springrabbitdemo.test;

import com.bise.mqdemo.bootstrap.MapServiceWebApplication;
import com.bise.mqdemo.sender.FanoutSender;
import com.bise.mqdemo.sender.NeoSender;
import com.bise.mqdemo.sender.Sender;
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
@SpringBootTest(classes = MapServiceWebApplication.class)
public class RabbitMqHelloTest {


    @Autowired
    private Sender helloSender;

    @Autowired
    private NeoSender neoSender;

    @Autowired
    private FanoutSender fanoutSender;

 /*   @Test
    public void hello() throws Exception {
        helloSender.send();
    }*/

    @Test
    public void oneToMany() throws Exception {
      /*  for (int i=0;i<103;i++){
            neoSender.send(i);
        }*/
    }
/*
    @Test
    public void fanoutToMany() throws Exception {
        for (int i=0;i<100;i++){
            fanoutSender.send(i);
        }
    }*/

}
