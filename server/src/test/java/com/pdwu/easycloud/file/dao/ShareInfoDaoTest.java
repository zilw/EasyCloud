package com.pdwu.easycloud.file.dao;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import com.pdwu.easycloud.file.bean.ShareInfoBean;
import com.pdwu.easycloud.file.constant.FileInfoConstant;
import com.pdwu.easycloud.file.constant.ShareInfoConstant;
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
 * Created by pdwu on 2017/12/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/resources")
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml", "classpath:spring/spring-mvc.xml", "classpath:spring/db-test.xml"})
public class ShareInfoDaoTest {

    public static DbSetupTracker dbSetupTracker = new DbSetupTracker();

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ShareInfoDao shareInfoDao;

    @Before
    public void setUp() throws Exception {

        Operation operation = sequenceOf(
                Operations.deleteAllFrom("file_info", "share_info"),
                Operations.insertInto("file_info")
                        .columns("FILE_ID", "USER_ID", "MD5", "PATH", "NAME", "SIZE", "STATUS", "CREATE_TIME", "LAST_TIME")
                        .values(101L, 10011L, "793914c9c583d9d86d0f4ed8c521b0c1", "/file/10011/", "1513317967830.png", 100L, FileInfoConstant.STATUS_DELETE, new Date(1513317967830L), new Date(1513317967830L))
                        .values(102L, 10011L, "c28cbd398a61e9022fd6a6835a57dc50", "/file/10011/", "1513317967833.zip", 100L, FileInfoConstant.STATUS_NORMAL, new Date(1513317967830L), new Date(1513317967830L))
                        .values(103L, 10012L, "099b3b060154898840f0ebdfb46ec78f", "/file/10012/", "1513317967835.png", 100L, FileInfoConstant.STATUS_NORMAL, new Date(1513317967830L), new Date(1513317967830L))
                        .values(104L, 10013L, "3b060154898840f0ebdfb46ec78f099b", "/file/10013/", "1513317967888.png", 100L, FileInfoConstant.STATUS_NORMAL, new Date(1513317967830L), new Date(1513317967830L))
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
    public void insertShareInfo() throws Exception {
        ShareInfoBean bean = new ShareInfoBean();
        bean.setCreateTime(new Date());
        bean.setLastTime(new Date());
        bean.setUserId(10099L);
        bean.setStatus(ShareInfoConstant.STATUS_NORMAL);
        bean.setFileId(105L);
        bean.setShareId(null);

        int res = shareInfoDao.insertShareInfo(bean);
        assertEquals(1, res);
        assertTrue(bean.getShareId() > 1);

        new ShareInfoConstant();
    }

    @Test
    public void updateShareInfoStatus() throws Exception {

        //R(shareId, status, lastTime)
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("shareId", 100123L);
        param.put("status", ShareInfoConstant.STATUS_CANCLE);
        param.put("lastTime", new Date());
        int res = shareInfoDao.updateShareInfoStatus(param);
        assertEquals(1, res);

    }

    @Test
    public void updateShareInfo() throws Exception {
        //R(shareId, lastTime) O(shortlink)
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("shareId", 100123L);
        param.put("shortlink", "12222af");
        param.put("lastTime", new Date());
        int res = shareInfoDao.updateShareInfo(param);
        assertEquals(1, res);
    }

    @Test
    public void selectShareInfo() throws Exception {

        //O(userId, shareId, status, fileId)
        Map<String, Object> param = new HashMap<String, Object>();


        //指定shareId
        param.put("shareId", 100001L);
        List<ShareInfoBean> list = shareInfoDao.selectShareInfo(param);
        assertEquals(1, list.size());
        //assert shareInfo
        assertEquals(101L, list.get(0).getFileId().longValue());
        assertEquals(10011L, list.get(0).getUserId().longValue());
        assertEquals(ShareInfoConstant.STATUS_CANCLE, list.get(0).getStatus().intValue());
        assertEquals(1513926315000L, list.get(0).getCreateTime().getTime());
        //assert fileInfo
        assertEquals(101L, list.get(0).getFileInfo().getFileId().longValue());
        assertEquals(10011L, list.get(0).getFileInfo().getUserId().longValue());
        assertEquals(FileInfoConstant.STATUS_DELETE, list.get(0).getFileInfo().getStatus().intValue());
        assertEquals("/file/10011/", list.get(0).getFileInfo().getTruePath());
        assertEquals("1513317967830.png", list.get(0).getFileInfo().getName());


        //指定用户
        param.clear();
        param.put("userId", 10011L);
        List<ShareInfoBean> list1 = shareInfoDao.selectShareInfo(param);
        assertEquals(3, list1.size());
        assertEquals(101L, list1.get(0).getFileId().longValue());
        assertEquals(102L, list1.get(1).getFileId().longValue());
        assertEquals(104L, list1.get(2).getFileId().longValue());

        //指定用户和status
        param.clear();
        param.put("status", ShareInfoConstant.STATUS_NORMAL);
        param.put("userId", 10011L);
        List<ShareInfoBean> list2 = shareInfoDao.selectShareInfo(param);
        assertEquals(1, list2.size());
        assertEquals(104L, list2.get(0).getFileId().longValue());

    }

    @Test
    public void delete() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("shareId", 100999L);

        //不存在
        int i = shareInfoDao.delete(param);
        assertEquals(0, i);

        param.clear();
        param.put("shareId", 100001L);
        int i2 = shareInfoDao.delete(param);
        assertEquals(1, i2);

    }

    @Test
    public void countShareList() throws Exception {
        dbSetupTracker.skipNextLaunch();

        //O(userId, shareId, status, fileId)
        Map<String, Object> param = new HashMap<String, Object>();

        //指定用户
        param.clear();
        param.put("userId", 10011L);
        assertEquals(3, shareInfoDao.countShareList(param));

        //指定用户和status
        param.clear();
        param.put("status", ShareInfoConstant.STATUS_NORMAL);
        param.put("userId", 10011L);
        assertEquals(1, shareInfoDao.countShareList(param));
    }

}