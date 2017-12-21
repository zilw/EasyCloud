package com.pdwu.easycloud.file.service.impl;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import com.pdwu.easycloud.common.bean.ResultCode;
import com.pdwu.easycloud.file.constant.FileInfoConstant;
import com.pdwu.easycloud.file.constant.ShareInfoConstant;
import com.pdwu.easycloud.file.service.IDownloadService;
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
 * Created by pdwu on 2017/12/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/resources")
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml", "classpath:spring/spring-mvc.xml","classpath:spring/db-test.xml"})
public class DownloadServiceImplTest {

    public static DbSetupTracker dbSetupTracker = new DbSetupTracker();

    @Autowired
    private DataSource dataSource;

    @Autowired
    private IDownloadService downloadService;

    @Before
    public void setUp() throws Exception {
        Operation operation = sequenceOf(
                Operations.deleteAllFrom("file_info", "share_info"),
                Operations.insertInto("file_info")
                        .columns("FILE_ID", "USER_ID", "MD5", "PATH", "NAME", "STATUS", "CREATE_TIME", "LAST_TIME")
                        .values(101L, 10011L, "793914c9c583d9d86d0f4ed8c521b0c1", "/file/10011/", "1513317967830.png", FileInfoConstant.STATUS_DELETE, new Date(1513317967830L), new Date(1513317967830L))
                        .values(102L, 10011L, "c28cbd398a61e9022fd6a6835a57dc50", "/file/10011/", "1513317967833.zip", FileInfoConstant.STATUS_NORMAL, new Date(1513317967830L), new Date(1513317967830L))
                        .values(103L, 10012L, "099b3b060154898840f0ebdfb46ec78f", "/file/10012/", "1513317967835.png", FileInfoConstant.STATUS_NORMAL, new Date(1513317967830L), new Date(1513317967830L))
                        .values(104L, 10013L, "3b060154898840f0ebdfb46ec78f099b", "/file/10013/", "1513317967888.png", FileInfoConstant.STATUS_NORMAL, new Date(1513317967830L), new Date(1513317967830L))
                        .build(),
                Operations.insertInto("share_info")
                        .columns("SHARE_ID", "FILE_ID", "USER_ID", "STATUS", "CREATE_TIME", "LAST_TIME")
                        .values(100001L, 101L, 10011L, ShareInfoConstant.STATUS_CANCLE, new Date(1513926315000L), new Date(1513926315000L))
                        .values(100012L, 102L, 10011L, ShareInfoConstant.STATUS_CANCLE, new Date(1513317967830L), new Date(1513317967830L))
                        .values(100123L, 103L, 10012L, ShareInfoConstant.STATUS_NORMAL, new Date(1513317967830L), new Date(1513317967830L))
                        .values(102224L, 104L, 10011L, ShareInfoConstant.STATUS_NORMAL, new Date(1513317967830L), new Date(1513317967830L))
                        .build()
        );

        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
        dbSetupTracker.launchIfNecessary(dbSetup);

    }

    @Test
    public void download() throws Exception {

        //1.1 文件不存在，真木有
        assertEquals(ResultCode.not_found, downloadService.download(null, 199L, null, null).getCode());

        //1.2 文件不存在，逻辑删除 STATUS_DELETE
        assertEquals(ResultCode.not_found, downloadService.download(null, 101L, null, null).getCode());

        //2.1.a 文件存在，但不是该用户的，也未分享
        assertEquals(ResultCode.not_found, downloadService.download(10099L, 102L, null, null).getCode());

        //2.1.b 文件存在，但不是该用户的；文件主人确实分享了
        assertEquals(200, downloadService.download(10099L, 103L, 100123L, null).getCode());

        //2.1.c 文件存在，但不是该用户的；文件主人取消分享了
        assertEquals(ResultCode.not_found, downloadService.download(10099L, 102L, 100012L, null).getCode());

        //2.2   文件存在，是本用户的
        assertEquals(200, downloadService.download(10011L, 102L, null, null).getCode());

    }

}