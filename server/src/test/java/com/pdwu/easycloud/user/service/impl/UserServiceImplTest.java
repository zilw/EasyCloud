package com.pdwu.easycloud.user.service.impl;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.common.bean.ResultCode;
import com.pdwu.easycloud.user.bean.UserBean;
import com.pdwu.easycloud.user.service.IUserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.junit.Assert.*;

/**
 * Created by pdwu on 2017/12/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/resources")
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml", "classpath:spring/spring-mvc.xml","classpath:spring/db-test.xml"})
public class UserServiceImplTest {


    public static DbSetupTracker dbSetupTracker = new DbSetupTracker();

    @Autowired
    private DataSource dataSource;

    @Autowired
    private IUserService userService;

    @Before
    public void setUp() throws Exception {

        //thisispassword md5: 81c93a6d22daaa5fdb4dbd4267e5e06e
        //mythisispassword md5: 28cc3ddb050d95a52aeb507bbd74c3e7

        Operation operation = sequenceOf(
                Operations.deleteAllFrom("user_info", "user_token"),
                Operations.insertInto("user_info")
                        .columns("USER_ID", "ACCOUNT", "PASSWORD", "CREATE_TIME", "LAST_TIME")
                        .values(10010L, "xiaoming", "81c93a6d22daaa5fdb4dbd4267e5e06e", new Date(1513317967830L), new Date(1513317967830L))
                        .values(10011L, "zhangsan", "81c93a6d22daaa5fdb4dbd4267e5e06e", new Date(1513317967840L), new Date(1513317967840L))
                        .values(10012L, "lisi", "28cc3ddb050d95a52aeb507bbd74c3e7", new Date(1513317967850L), new Date(1513317967850L))
                        .build(),
                Operations.insertInto("user_token")
                        .columns("USER_ID", "TOKEN", "STATUS", "CREATE_TIME", "LAST_TIME")
                        .values(10012L, "12d8aa790b4444aa9796c92cf8af28d9", 1, new Date(1513317967850L), new Date(1513317967860L))
                        .values(10012L, "13d8aa790b4444aa9796c92cf8af28d9", 0, new Date(1513317967860L), new Date(1513317967860L))
                        .values(10010L, "14d8aa790b4444aa9796c92cf8af28d9", 0, new Date(1513317967850L), new Date(1513317967850L))
                        .build()
        );

        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
        dbSetupTracker.launchIfNecessary(dbSetup);

    }

    @Test
    public void login() throws Exception {

        dbSetupTracker.skipNextLaunch();

        //参数校验
        assertNull(userService.login(null, null));
        assertNull(userService.login("", null));
        assertNull(userService.login("x", null));

        //用户不存在
        UserBean bean = userService.login("nothisaccount", "mypassword");
        assertNull(bean);

        //用户存在，密码不正确
        UserBean bean1 = userService.login("xiaoming", "errorpassword");
        assertNull(bean1);

        //登录成功
        UserBean bean2 = userService.login("xiaoming", "thisispassword");
        assertEquals(10010L, bean2.getUserId().longValue());
        assertEquals(null, bean2.getPassword());
        assertEquals(1513317967830L, bean2.getCreateTime().getTime());
        assertNotNull(bean2.getToken()); //登录成功带上token

    }

    @Test
    public void register() throws Exception {

        //参数校验
        ResultBean bean = userService.register(null, null);
        assertEquals(ResultCode.fail, bean.getCode());

        //注册成功
        ResultBean bean1 = userService.register("newUser", "mypasswordOOO");
        assertEquals(ResultCode.ok, bean1.getCode());

        //用户已存在
        ResultBean bean2 = userService.register("xiaoming", "xiaomingpassword");
        assertEquals(ResultCode.fail, bean2.getCode());


    }

    @Test
    public void getUserInfo() throws Exception {
        dbSetupTracker.skipNextLaunch();

        //参数校验
        assertNull(userService.getUserInfo(null));

        Map<String, Object> param = new HashMap<String, Object>();

        //userId存在
        param.put("userId", 10010L);
        UserBean bean = userService.getUserInfo(param);
        assertEquals("xiaoming", bean.getAccount());


        //account存在
        param.clear();
        param.put("account", "zhangsan");
        UserBean bean1 = userService.getUserInfo(param);
        assertEquals(10011L, bean1.getUserId().longValue());

        //userId不存在
        param.clear();
        param.put("userId", 9999L);
        UserBean bean2 = userService.getUserInfo(param);
        assertNull(null, bean2);

    }

    @Test
    public void logout() throws Exception {

        //参数
        assertEquals(400, userService.logout(null).getCode());

        //不存在的token
        assertEquals(400, userService.logout("nothisToken").getCode());

        //正常
        assertEquals(200, userService.logout("14d8aa790b4444aa9796c92cf8af28d9").getCode());


    }

}