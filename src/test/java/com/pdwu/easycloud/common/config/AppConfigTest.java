package com.pdwu.easycloud.common.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 * Created by pdwu on 2017/12/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/resources")
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml", "classpath:spring/spring-mvc.xml"})
public class AppConfigTest {

    @Autowired
    private AppConfig appConfig;

    @Test
    public void testAppConfig() {
        assertTrue(appConfig.getUserFilePath().length() > 2);
        assertTrue(!appConfig.getUserFilePath().contains("userfilepath"));
        System.out.println(appConfig.getUserFilePath());

        new AppConfig();
        new AppConfig().setUserFilePath("");
    }


}