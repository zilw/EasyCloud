package com.pdwu.easycloud.user.dao;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import com.pdwu.easycloud.user.bean.TokenBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
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
@WebAppConfiguration("src/test/resources")
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml", "classpath:spring/spring-mvc.xml","classpath:spring/db-test.xml"})
public class TokenDaoTest {

    public static DbSetupTracker dbSetupTracker = new DbSetupTracker();

    @Autowired
    private DataSource dataSource;

    @Autowired
    private TokenDao tokenDao;


    @Before
    public void setUp() throws Exception {
        Operation operation = sequenceOf(
                Operations.deleteAllFrom("user_info", "user_token"),
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
    public void selectToken() throws Exception {
        dbSetupTracker.skipNextLaunch();

        Map<String, Object> param = new HashMap<String, Object>();

        //token存在
        param.put("token", "12d8aa790b4444aa9796c92cf8af28d9");
        List<TokenBean> list1 = tokenDao.selectToken(param);
        assertEquals(10012L, list1.get(0).getUserId().longValue());
        assertEquals("12d8aa790b4444aa9796c92cf8af28d9", list1.get(0).getToken());
        assertEquals(1513317967850L, list1.get(0).getCreateTime().getTime());

        //token不存在
        param.clear();
        param.put("token", "xxxxaa790b4444aa9796c92cf8af2xxx");
        List<TokenBean> list2 = tokenDao.selectToken(param);
        assertEquals(0, list2.size());

        //指定token和status，存在
        param.clear();
        param.put("token", "13d8aa790b4444aa9796c92cf8af28d9");
        param.put("status", 0);
        List<TokenBean> list3 = tokenDao.selectToken(param);
        assertEquals(10012L, list3.get(0).getUserId().longValue());


        //指定token和status，不存在
        param.clear();
        param.put("token", "13d8aa790b4444aa9796c92cf8af28d9");
        param.put("status", 1);
        List<TokenBean> list4 = tokenDao.selectToken(param);
        assertEquals(0, list4.size());

        //指定userId，存在（两条 10012L ）
        param.clear();
        param.put("userId", 10012L);
        List<TokenBean> list5 = tokenDao.selectToken(param);
        assertEquals(2, list5.size());

    }

    @Test
    public void insertToken() throws Exception {

        TokenBean bean = new TokenBean();
        bean.setCreateTime(new Date());
        bean.setLastTime(new Date());
        bean.setStatus(0);
        bean.setToken("123455790b4444aa9796c92cf8af2yyy");
        bean.setUserId(10099L);

        int affectedRows = tokenDao.insertToken(bean);
        assertEquals(1, affectedRows);
    }

    @Test
    public void updateTokenStatus() throws Exception {

        Map<String, Object> param = new HashMap<String, Object>();

        //token不存在
        param.put("token", "ooooooopxxx");
        param.put("status", 1);
        int affectedRows = tokenDao.updateTokenStatus(param);
        assertEquals(0, affectedRows);

        //token存在
        param.clear();
        param.put("token", "14d8aa790b4444aa9796c92cf8af28d9");
        param.put("status", 1);
        int affectedRows2 = tokenDao.updateTokenStatus(param);
        assertEquals(1, affectedRows2);

    }

}