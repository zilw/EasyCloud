package com.pdwu.easycloud.user.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.pdwu.easycloud.user.bean.UserBean;
import com.pdwu.easycloud.user.dao.UserDao;
import com.pdwu.easycloud.user.service.IUserService;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/resources")
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml", "classpath:spring/spring-mvc.xml"})
public class UserServiceTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        cleanUserInfoTable();

    }


    @Autowired
    public IUserService userService;

    @Autowired
    public UserDao userDao;

    @Autowired
    public SqlSessionFactory sqlSessionFactory;

    @Test
    public void testLogin() {

        //用户不存在
        Assert.assertNull(userService.login("ll", "fa"));

        String account = "userA";
        String password = "oooasdd";

        UserBean bean = new UserBean();
        bean.setAccount(account);
        bean.setPassword(password);
        Date now = new Date();
        bean.setCreateTime(now);
        bean.setLastTime(now);
        userDao.insertUser(bean);

        //登录成功
        UserBean result = userService.login(account, password);
        Assert.assertTrue(result.getAccount().equals(account));
        Assert.assertNull(result.getPassword()); //不返回密码
        Assert.assertEquals(now.getTime(), result.getCreateTime().getTime());
        Assert.assertEquals(now.getTime(), result.getLastTime().getTime());

        //密码错误
        UserBean result2 = userService.login(account, password + "abc");
        Assert.assertNull(result2);

    }

    private void cleanUserInfoTable() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.delete("deleteAllInfo");
    }


}
