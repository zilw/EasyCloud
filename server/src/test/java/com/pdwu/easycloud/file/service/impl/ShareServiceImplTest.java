package com.pdwu.easycloud.file.service.impl;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import com.pdwu.easycloud.file.bean.ShareInfoBean;
import com.pdwu.easycloud.file.constant.FileInfoConstant;
import com.pdwu.easycloud.file.constant.ShareInfoConstant;
import com.pdwu.easycloud.file.service.IShareService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;

import java.util.Date;
import java.util.List;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.junit.Assert.*;

/**
 * Created by pdwu on 2017/12/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/resources")
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml", "classpath:spring/spring-mvc.xml", "classpath:spring/db-test.xml"})
public class ShareServiceImplTest {


    public static DbSetupTracker dbSetupTracker = new DbSetupTracker();

    @Autowired
    private DataSource dataSource;

    @Autowired
    private IShareService shareService;

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
                        .values(103224L, 1055L, 100112L, ShareInfoConstant.STATUS_NORMAL, new Date(1513317967830L), new Date(1513317967830L))
                        .values(104224L, 1055L, 100112L, ShareInfoConstant.STATUS_NORMAL, new Date(1513317967830L), new Date(1513317967830L))
                        .build()
        );

        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
        dbSetupTracker.launchIfNecessary(dbSetup);
    }

    @Test
    public void insertShareInfo() throws Exception {
        //arg
        assertEquals(400, shareService.insertShareInfo(null, null).getCode());

        assertEquals(200, shareService.insertShareInfo(10099L, 105L).getCode());
    }

    @Test
    public void deleteShareInfo() throws Exception {
        //arg
        assertEquals(400, shareService.deleteShareInfo(null).getCode());

        //成功
        assertEquals(200, shareService.deleteShareInfo(100123L).getCode());

        //shareId不存在
        assertEquals(400, shareService.deleteShareInfo(100999L).getCode());
    }

    @Test
    public void deleteShareInfoByFileId() throws Exception {

        //arg
        assertEquals(400, shareService.deleteShareInfoByFileId(null).getCode());

        //成功
        assertEquals(200, shareService.deleteShareInfoByFileId(1055L).getCode());

        //file不存在
        assertEquals(400, shareService.deleteShareInfoByFileId(100999L).getCode());
    }

    @Test
    public void getShareFileInfoById() throws Exception {

        //arg
        assertNull(shareService.getShareFileInfoById(null));

        //成功
        ShareInfoBean bean = shareService.getShareFileInfoById(100123L);
        assertEquals(103L, bean.getFileId().longValue());
        assertEquals(103L, bean.getFileInfo().getFileId().longValue());
        assertEquals("099b3b060154898840f0ebdfb46ec78f", bean.getFileInfo().getMd5());

        //不存在
        assertNull(shareService.getShareFileInfoById(100999L));
    }

    @Test
    public void listUserShareInfos() throws Exception {

        //arg
        List<ShareInfoBean> list = shareService.listUserShareInfos(null, null, 1, 10);
        assertEquals(0, list.size());

        //用户10011L
        List<ShareInfoBean> list1 = shareService.listUserShareInfos(10011L, null, 1, 10);
        assertEquals(3, list1.size());


        //用户10011L, STATUS_CANCLE
        List<ShareInfoBean> list2 = shareService.listUserShareInfos(10011L, ShareInfoConstant.STATUS_CANCLE, 1, 10);
        assertEquals(2, list2.size());
        assertEquals(101L, list2.get(0).getFileId().longValue());
        assertEquals(102L, list2.get(1).getFileId().longValue());


        //用户10011L, STATUS_NORMAL
        List<ShareInfoBean> list3 = shareService.listUserShareInfos(10011L, ShareInfoConstant.STATUS_NORMAL, 1, 10);
        assertEquals(1, list3.size());
        assertEquals(104L, list3.get(0).getFileId().longValue());
    }

    @Test
    public void countShareList() throws Exception {

        assertEquals(6, shareService.countShareList(null, null));

        assertEquals(3, shareService.countShareList(10011L, null));

        assertEquals(1, shareService.countShareList(10011L, ShareInfoConstant.STATUS_NORMAL));

    }

}