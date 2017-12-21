package com.pdwu.easycloud.user.dao;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import com.pdwu.easycloud.user.bean.UserBean;
import org.junit.Assert;
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
import java.util.List;
import java.util.Map;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.junit.Assert.*;

/**
 * Created by pdwu on 2017/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/resources")
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml", "classpath:spring/spring-mvc.xml","classpath:spring/db-test.xml"})
public class UserDaoTest {

    public static DbSetupTracker dbSetupTracker = new DbSetupTracker();

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDao userDao;

    @Before
    public void setUp() throws Exception {

        Operation operation = sequenceOf(
                Operations.deleteAllFrom("user_info", "user_token"),
                Operations.insertInto("user_info")
                        .columns("USER_ID", "ACCOUNT", "PASSWORD", "CREATE_TIME", "LAST_TIME")
                        .values(10010L, "xiaoming", "thisispassword", new Date(1513317967830L), new Date(1513317967830L))
                        .values(10011L, "zhangsan", "thisispassword", new Date(1513317967840L), new Date(1513317967840L))
                        .values(10012L, "lisi", "mythisispassword", new Date(1513317967850L), new Date(1513317967850L))
                        .build()
        );

        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
        dbSetupTracker.launchIfNecessary(dbSetup);

    }

    @Test
    public void insertUser() throws Exception {

        UserBean bean = new UserBean();
        bean.setAccount("testUser");
        bean.setPassword("testInsertUserPsd");
        bean.setLastTime(new Date(1513317967830L));
        bean.setCreateTime(new Date(1513317967830L));

        this.userDao.insertUser(bean);

        assertNotNull(bean.getUserId());
    }

    @Test
    public void selectUserByAccountAndPassword() throws Exception {

        dbSetupTracker.skipNextLaunch();

        //存在用户 密码正确
        UserBean param = new UserBean();
        param.setAccount("xiaoming");
        param.setPassword("thisispassword");
        UserBean userBean = userDao.selectUserByAccountAndPassword(param);
        assertTrue(userBean.getAccount().equals("xiaoming"));
        assertEquals(null, userBean.getPassword());
        assertEquals(1513317967830L, userBean.getCreateTime().getTime());
        assertEquals(1513317967830L, userBean.getLastTime().getTime());

        //存在用户，密码错误
        param.setPassword("xxx");
        UserBean bean2 = userDao.selectUserByAccountAndPassword(param);
        assertNull(bean2);

        //用户不存在
        param.setAccount("xiaohong");
        param.setPassword("sss");
        UserBean bean3 = userDao.selectUserByAccountAndPassword(param);
        assertNull(bean3);
    }

    @Test
    public void selectUserAccountCount() throws Exception {
        dbSetupTracker.skipNextLaunch();

        //有一个用户
        int count = this.userDao.selectUserAccountCount("xiaoming");
        assertEquals(1, count);

        //不存在该用户
        int count2 = this.userDao.selectUserAccountCount("oop");
        assertEquals(0, count2);

    }

    @Test
    public void selectUserInfo() throws Exception {

        //按userId找xiaoming
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", "10010");
        List<UserBean> list = this.userDao.selectUserInfo(param);
        assertEquals("xiaoming", list.get(0).getAccount());

        //按account找zhangsan
        param.clear();
        param.put("account", "zhangsan");
        List<UserBean> list2 = this.userDao.selectUserInfo(param);
        assertEquals(10011L, list2.get(0).getUserId().longValue());

        //不存在 userId
        param.clear();
        param.put("userId", "10099");
        List<UserBean> list3 = this.userDao.selectUserInfo(param);
        assertEquals(0, list3.size()); //返回空数组

        //按userId和account找
        param.clear();
        param.put("userId", "10012");
        param.put("account", "lisi");
        List<UserBean> list4 = this.userDao.selectUserInfo(param);
        assertEquals(10012L, list4.get(0).getUserId().longValue());
        assertEquals(1513317967850L, list4.get(0).getCreateTime().getTime());
    }

}