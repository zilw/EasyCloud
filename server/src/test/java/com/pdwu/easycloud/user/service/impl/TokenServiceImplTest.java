package com.pdwu.easycloud.user.service.impl;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.common.bean.ResultCode;
import com.pdwu.easycloud.user.bean.TokenBean;
import com.pdwu.easycloud.user.constant.TokenConstant;
import com.pdwu.easycloud.user.service.ITokenService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;

import java.util.Date;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.junit.Assert.*;

/**
 * Created by pdwu on 2017/12/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/resources")
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml", "classpath:spring/spring-mvc.xml","classpath:spring/db-test.xml"})
public class TokenServiceImplTest {


    public static DbSetupTracker dbSetupTracker = new DbSetupTracker();

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ITokenService tokenService;

    @Before
    public void setUp() throws Exception {

        Operation operation = sequenceOf(
                Operations.deleteAllFrom("user_info", "user_token"),
                Operations.insertInto("user_token")
                        .columns("USER_ID", "TOKEN", "STATUS", "CREATE_TIME", "LAST_TIME")
                        .values(10012L, "12d8aa790b4444aa9796c92cf8af28d9", 1, new Date(1513317967850L), new Date(1513317967860L))
                        .values(10012L, "13d8aa790b4444aa9796c92cf8af28d9", 0, new Date(1513317967860L), new Date(1513317967860L))
                        .values(10010L, "14d8aa790b4444aa9796c92cf8af28d9", 0, new Date(), new Date())
                        .values(10019L, "19d8aa790b4444aa9796c92cf8af28d9", 0, new Date(1513317967860L), new Date(1513317967860L))
                        .build()
        );

        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
        dbSetupTracker.launchIfNecessary(dbSetup);

    }

    @Test
    public void addToken() throws Exception {

        TokenBean bean = tokenService.addToken(10055L);
        assertNotNull(bean.getCreateTime());
        assertNotNull(bean.getLastTime());
        assertNotNull(bean.getStatus());
        assertNotNull(bean.getToken());
        assertNotNull(bean.getUserId());
    }

    @Test
    public void checkTokenValid() throws Exception {

        //参数校验
        assertEquals(400, tokenService.checkTokenValid(null).getCode());

        //不存在的token
        assertEquals(1, tokenService.checkTokenValid("nothistoken").getCode());

        //失效的token
        assertEquals(1, tokenService.checkTokenValid("12d8aa790b4444aa9796c92cf8af28d9").getCode());

        //有效的token 10010L
        assertEquals(ResultCode.ok, tokenService.checkTokenValid("14d8aa790b4444aa9796c92cf8af28d9").getCode());

        //数据库状态有效，但取出后再比较过期时间，会无效的token  10019L
        assertEquals(1, tokenService.checkTokenValid("19d8aa790b4444aa9796c92cf8af28d9").getCode());


    }

    @Test
    public void updateTokenStatus() throws Exception {
        //参数
        assertEquals(400, tokenService.updateTokenStatus(null, 1).getCode());

        //成功
        ResultBean bean = tokenService.updateTokenStatus("14d8aa790b4444aa9796c92cf8af28d9", TokenConstant.STATUS_DELETE);
        assertEquals(200, bean.getCode());

        //状态已被更改 (还是会成功）
        ResultBean bean1 = tokenService.updateTokenStatus("12d8aa790b4444aa9796c92cf8af28d9", TokenConstant.STATUS_DELETE);
        assertEquals(200, bean1.getCode());

        //token不存在
        ResultBean bean2 = tokenService.updateTokenStatus("faopsfjaf", TokenConstant.STATUS_DELETE);
        assertEquals(400, bean2.getCode());

    }

}