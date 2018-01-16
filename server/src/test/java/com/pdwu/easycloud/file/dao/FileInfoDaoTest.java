package com.pdwu.easycloud.file.dao;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import com.pdwu.easycloud.file.bean.FileInfoBean;
import com.pdwu.easycloud.file.constant.FileInfoConstant;
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
public class FileInfoDaoTest {

    public static DbSetupTracker dbSetupTracker = new DbSetupTracker();

    @Autowired
    private DataSource dataSource;

    @Autowired
    private FileInfoDao fileInfoDao;

    @Before
    public void setUp() throws Exception {
        Operation operation = sequenceOf(
                Operations.deleteAllFrom("file_info"),
                Operations.insertInto("file_info")
                        .columns("FILE_ID", "USER_ID", "MD5", "PATH", "NAME", "SIZE", "STATUS", "CREATE_TIME", "LAST_TIME")
                        .values(101L, 10011L, "793914c9c583d9d86d0f4ed8c521b0c1", "/file/10011/", "1513317967830.png", 100L, FileInfoConstant.STATUS_DELETE, new Date(1513317967830L), new Date(1513317967830L))
                        .values(102L, 10011L, "c28cbd398a61e9022fd6a6835a57dc50", "/file/10011/", "1513317967833.zip", 100L, FileInfoConstant.STATUS_NORMAL, new Date(1513317967830L), new Date(1513317967830L))
                        .values(103L, 10012L, "099b3b060154898840f0ebdfb46ec78f", "/file/10012/", "1513317967835.png", 100L, FileInfoConstant.STATUS_NORMAL, new Date(1513317967830L), new Date(1513317967830L))
                        .build()
        );

        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
        dbSetupTracker.launchIfNecessary(dbSetup);
    }

    @Test
    public void insertFileInfo() throws Exception {
        FileInfoBean bean = new FileInfoBean();
        bean.setCreateTime(new Date());
        bean.setLastTime(new Date());
        bean.setMd5("7b8b965ad4bca0e41ab51de7b31363a1");
        bean.setName("1513317967899.png");
        bean.setTruePath("/file/10099/");
        bean.setUserId(10099L);
        bean.setStatus(FileInfoConstant.STATUS_NORMAL);
        bean.setFileId(null);

        int i = fileInfoDao.insertFileInfo(bean);
        assertEquals(i, 1);
        assertTrue(bean.getFileId() > 100L);

        new FileInfoConstant();

    }

    @Test
    public void selectFileInfoList() throws Exception {
        dbSetupTracker.skipNextLaunch();

        //O(userId, fileId, md5, status, pageIndex & pageCount)
        Map<String, Object> param = new HashMap<String, Object>();

        //查找所有
        List<FileInfoBean> list = fileInfoDao.selectFileInfoList(param);
        assertEquals(3, list.size());

        //根据userId查找
        param.put("userId", 10011L);
        List<FileInfoBean> list1 = fileInfoDao.selectFileInfoList(param);
        assertEquals(2, list1.size());

        //根据MD5查找
        param.clear();
        param.put("md5", "099b3b060154898840f0ebdfb46ec78f");
        List<FileInfoBean> list2 = fileInfoDao.selectFileInfoList(param);
        assertEquals(103L, list2.get(0).getFileId().longValue());
        assertEquals(10012L, list2.get(0).getUserId().longValue());
        assertEquals("/file/10012/", list2.get(0).getTruePath());
        assertEquals("1513317967835.png", list2.get(0).getName());
        assertEquals(100L, list2.get(0).getSize().longValue());
        assertEquals(1513317967830L, list2.get(0).getCreateTime().getTime());
        assertEquals(1513317967830L, list2.get(0).getLastTime().getTime());

        //根据fileId查找
        param.clear();
        param.put("fileId", 103L);
        List<FileInfoBean> list3 = fileInfoDao.selectFileInfoList(param);
        assertEquals(103L, list3.get(0).getFileId().longValue());
        assertEquals(10012L, list3.get(0).getUserId().longValue());
        assertEquals("/file/10012/", list3.get(0).getTruePath());
        assertEquals("1513317967835.png", list3.get(0).getName());
        assertEquals(1513317967830L, list3.get(0).getCreateTime().getTime());
        assertEquals(1513317967830L, list3.get(0).getLastTime().getTime());

        //根据userId和status查找
        param.clear();
        param.put("userId", 10011L);
        param.put("status", FileInfoConstant.STATUS_NORMAL);
        List<FileInfoBean> list4 = fileInfoDao.selectFileInfoList(param);
        assertEquals(1, list4.size());  //只有第二条数据
        assertEquals(102L, list4.get(0).getFileId().longValue());
        assertEquals(10011L, list4.get(0).getUserId().longValue());
        assertEquals("c28cbd398a61e9022fd6a6835a57dc50", list4.get(0).getMd5());

        //找不到
        param.clear();
        param.put("userId", 10077L);
        List<FileInfoBean> list5 = fileInfoDao.selectFileInfoList(param);
        assertEquals(0, list5.size());
    }

    @Test
    public void updateFileInfo() throws Exception {

        //O(name, status), R(fileId, lastTime)
        Map<String, Object> param = new HashMap<String, Object>();

        //根据fileId更新文件名
        param.put("fileId", 101L);
        param.put("name", "newFileName.png");
        param.put("lastTime", new Date());
        int updatedCount = fileInfoDao.updateFileInfo(param);
        assertEquals(1, updatedCount);

        //根据fileId更新文件名，fileId不存在
        param.put("fileId", 111L);
        param.put("name", "newFileName.png");
        param.put("lastTime", new Date());
        int updatedCount1 = fileInfoDao.updateFileInfo(param);
        assertEquals(0, updatedCount1);

        //根据fileId更新文件状态 （删除）
        param.clear();
        param.put("fileId", 103L);
        param.put("status", FileInfoConstant.STATUS_DELETE);
        param.put("lastTime", new Date());
        int updatedCount2 = fileInfoDao.updateFileInfo(param);
        assertEquals(1, updatedCount2);

    }

    @Test
    public void countFileList() throws Exception {

        dbSetupTracker.skipNextLaunch();

        //O(userId, status)
        Map<String, Object> param = new HashMap<String, Object>();

        //统计当前所有
        assertEquals(3, fileInfoDao.countFileList(param));

        //统计用户
        param.put("userId", 10011L);
        assertEquals(2, fileInfoDao.countFileList(param));

        //统计用户，包含状态
        param.clear();
        param.put("userId", 10011L);
        param.put("status", FileInfoConstant.STATUS_NORMAL);
        assertEquals(1, fileInfoDao.countFileList(param));

    }

}