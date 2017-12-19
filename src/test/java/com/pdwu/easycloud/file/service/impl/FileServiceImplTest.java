package com.pdwu.easycloud.file.service.impl;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.file.bean.FileInfoBean;
import com.pdwu.easycloud.file.constant.FileInfoConstant;
import com.pdwu.easycloud.file.service.IFileService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.junit.Assert.*;

/**
 * Created by pdwu on 2017/12/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/resources")
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml", "classpath:spring/spring-mvc.xml"})
public class FileServiceImplTest {

    public static DbSetupTracker dbSetupTracker = new DbSetupTracker();

    @Autowired
    private DataSource dataSource;

    @Autowired
    private IFileService fileService;


    @Before
    public void setUp() throws Exception {

        Operation operation = sequenceOf(
                Operations.deleteAllFrom("file_info"),
                Operations.insertInto("file_info")
                        .columns("FILE_ID", "USER_ID", "MD5", "PATH", "NAME", "STATUS", "CREATE_TIME", "LAST_TIME")
                        .values(101L, 10011L, "793914c9c583d9d86d0f4ed8c521b0c1", "/file/10011/", "1513317967830.png", FileInfoConstant.STATUS_DELETE, new Date(1513317967830L), new Date(1513317967830L))
                        .values(102L, 10011L, "c28cbd398a61e9022fd6a6835a57dc50", "/file/10011/", "1513317967833.zip", FileInfoConstant.STATUS_NORMAL, new Date(1513317967830L), new Date(1513317967830L))
                        .values(103L, 10012L, "099b3b060154898840f0ebdfb46ec78f", "/file/10012/", "1513317967835.png", FileInfoConstant.STATUS_NORMAL, new Date(1513317967830L), new Date(1513317967830L))
                        .build()
        );

        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
        dbSetupTracker.launchIfNecessary(dbSetup);

    }

    @Test
    public void insertFileInfo() throws Exception {

        //arg
        assertEquals(400, fileService.insertFileInfo(null).getCode());


        FileInfoBean bean = new FileInfoBean();
        bean.setCreateTime(new Date());
        bean.setLastTime(new Date());
        bean.setMd5("7b8b965ad4bca0e41ab51de7b31363a1");
        bean.setName("1513317967899.png");
        bean.setPath("/file/10099/");
        bean.setUserId(10099L);
        bean.setStatus(FileInfoConstant.STATUS_NORMAL);
        bean.setFileId(null);

        ResultBean resultBean = fileService.insertFileInfo(bean);
        assertEquals(200, resultBean.getCode());

    }

    @Test
    public void updateFileName() throws Exception {

        //arg
        assertEquals(400, fileService.updateFileName(null, "").getCode());
        assertEquals(400, fileService.updateFileName(100L, "").getCode());

        //成功
        assertEquals(200, fileService.updateFileName(101L, "ooop.jpg").getCode());

        //fileId不存在
        assertEquals(400, fileService.updateFileName(1000L, "xxx.png").getCode());

    }

    @Test
    public void deleteFileInfo() throws Exception {

        //arg
        assertEquals(400, fileService.deleteFileInfo(null).getCode());

        //成功
        assertEquals(200, fileService.deleteFileInfo(101L).getCode());

        //fileId不存在
        assertEquals(400, fileService.deleteFileInfo(1000L).getCode());

    }

    @Test
    public void listUserFiles() throws Exception {

        //arg
        List<FileInfoBean> list = fileService.listUserFiles(null, null);
        assertEquals(0, list.size());

        //用户10011L
        list = fileService.listUserFiles(10011L, null);
        assertEquals(2, list.size());
        assertEquals(101L, list.get(0).getFileId().longValue());
        assertEquals(102L, list.get(1).getFileId().longValue());

        //用户10011L, STATUS_DELETE
        List<FileInfoBean> list1 = fileService.listUserFiles(10011L, FileInfoConstant.STATUS_DELETE);
        assertEquals(1, list1.size());
        assertEquals(101L, list1.get(0).getFileId().longValue());

        //用户10011L, STATUS_NORMAL
        List<FileInfoBean> list2 = fileService.listUserFiles(10011L, FileInfoConstant.STATUS_NORMAL);
        assertEquals(1, list2.size());
        assertEquals(102L, list2.get(0).getFileId().longValue());

        //用户19999L, 不存在
        List<FileInfoBean> list3 = fileService.listUserFiles(19999L, null);
        assertEquals(0, list3.size());

    }

}