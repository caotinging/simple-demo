package ymldemo.test;

import com.caotinging.ymldemo.application.YmlValueApplication;
import com.caotinging.ymldemo.config.OrgProperties;
import com.caotinging.ymldemo.config.ServerProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @program: simple-demo
 * @description: 单元测试类
 * @author: CaoTing
 * @date: 2019/6/3
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = YmlValueApplication.class)
public class AppYmlValueTest {

    @Autowired
    private ServerProperties config;

    @Autowired
    private OrgProperties orgProperties;

    @Test
    public void printConfigs() {
        System.out.println(this.config.getUrl());
        System.out.println(this.config.getApp().getName());
        System.out.println(this.config.getApp().getThreadCount());
        System.out.println(this.config.getApp().getUsers());
    }

    @Test
    public void printOrgList() {
        for (String s : orgProperties.getOrgs()) {
            System.out.println(s);
        }
    }
}
