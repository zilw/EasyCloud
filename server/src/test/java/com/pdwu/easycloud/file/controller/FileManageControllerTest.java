package com.pdwu.easycloud.file.controller;

import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.common.config.AppConfig;
import com.pdwu.easycloud.file.bean.FileInfoBean;
import com.pdwu.easycloud.file.bean.ShareInfoBean;
import com.pdwu.easycloud.file.constant.FileInfoConstant;
import com.pdwu.easycloud.file.service.IFileService;
import com.pdwu.easycloud.file.service.IShareService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by pdwu on 2017/12/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/resources")
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml", "classpath:spring/spring-mvc.xml"})
public class FileManageControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private FileManageController controller;

    @Mock
    private IFileService fileService;
    @Mock
    private IShareService shareService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @Test
    public void uploadFile() throws Exception {

        MockMultipartFile file = new MockMultipartFile("file", "thiiscontent".getBytes());

        Mockito.when(fileService.uploadFile(1001L, file)).thenReturn(ResultBean.success(new FileInfoBean()));

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", 1001L);
        mockMvc.perform(fileUpload(AppConfig.API_FILE_UPLOAD).file(file)
                .session(session))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.data", notNullValue()));

    }

    @Test
    public void listMyFiles() throws Exception {

        Mockito.when(fileService.listUserFiles(1001L, FileInfoConstant.STATUS_NORMAL, 1, 10)).thenAnswer(new Answer<List<FileInfoBean>>() {
            public List<FileInfoBean> answer(InvocationOnMock invocationOnMock) throws Throwable {
                List<FileInfoBean> list = new ArrayList<FileInfoBean>();
                list.add(new FileInfoBean());
                list.add(new FileInfoBean());
                return list;
            }
        });
        Mockito.when(fileService.countUserFiles(1001L,FileInfoConstant.STATUS_NORMAL))
                .thenReturn(25);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", 1001L);
        mockMvc.perform(get(AppConfig.API_FILE_LIST).session(session))
                .andDo(print())
                .andExpect(jsonPath("$.data.userId", equalTo(1001)))
                .andExpect(jsonPath("$.data.list.length()", is(2)));

        mockMvc.perform(get(AppConfig.API_FILE_LIST)
                .session(session)
                .param("pageNum", "1")
                .param("pageSize", "10"))
                .andDo(print())
                .andExpect(jsonPath("$.data.userId", equalTo(1001)))
                .andExpect(jsonPath("$.data.totalPage", equalTo(3)))
                .andExpect(jsonPath("$.data.totalNumber", equalTo(25)))
                .andExpect(jsonPath("$.data.list.length()", is(2)));
    }

    @Test
    public void rename() throws Exception {
        Mockito.when(fileService.updateFileName(111L, "xx.jpg")).thenReturn(ResultBean.fail(""));

        mockMvc.perform(post(AppConfig.API_FILE_RENAME).param("fileId", "111")
                .param("filename", "xx.jpg"))
                .andExpect(jsonPath("$.code", is(400)));

        //不能Get
        mockMvc.perform(get(AppConfig.API_FILE_RENAME).param("fileId", "111")
                .param("filename", "xx.jpg"))
                .andExpect(status().is(405));

    }

    @Test
    public void delete() throws Exception {

        Mockito.when(fileService.deleteFileInfo(123L)).thenReturn(ResultBean.fail(""));


        mockMvc.perform(post(AppConfig.API_FILE_DELETE).param("fileId", "123"))
                .andDo(print())
                .andExpect(jsonPath("$.code", is(400)));

        mockMvc.perform(get(AppConfig.API_FILE_DELETE).param("fileId", "123"))
                .andExpect(status().is(405));

    }

    @Test
    public void share() throws Exception {
        Mockito.when(shareService.insertShareInfo(1001L, 123L)).thenReturn(ResultBean.fail(""));

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", 1001L);

        mockMvc.perform(post(AppConfig.API_FILE_SHARE).param("fileId", "123")
                .session(session))
                .andDo(print())
                .andExpect(jsonPath("$.code", is(400)));

        mockMvc.perform(get(AppConfig.API_FILE_SHARE).param("fileId", "123"))
                .andExpect(status().is(405));

    }

    @Test
    public void cancelShare() throws Exception {
        Mockito.when(shareService.deleteShareInfo(111L)).thenReturn(ResultBean.success(""));

        mockMvc.perform(post(AppConfig.API_FILE_CANCEL_SHARE).param("shareId", "111"))
                .andDo(print())
                .andExpect(jsonPath("$.code", is(200)));

        mockMvc.perform(get(AppConfig.API_FILE_CANCEL_SHARE).param("shareId", "111"))
                .andDo(print())
                .andExpect(status().is(405));

    }

    @Test
    public void shareList() throws Exception {
        Mockito.when(shareService.listUserShareInfos(1001L, null, 1, 10)).thenAnswer(new Answer<List<ShareInfoBean>>() {
            public List<ShareInfoBean> answer(InvocationOnMock invocationOnMock) throws Throwable {

                List<ShareInfoBean> list = new ArrayList<ShareInfoBean>();
                list.add(new ShareInfoBean());
                list.add(new ShareInfoBean());
                return list;
            }
        });

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", 1001L);
        mockMvc.perform(get(AppConfig.API_FILE_SHARE_LIST).session(session))
                .andDo(print())
                .andExpect(jsonPath("$.data.list.length()", is(2)));

        mockMvc.perform(get(AppConfig.API_FILE_SHARE_LIST)
                .session(session)
                .param("pageNum", "1")
                .param("pageSize", "10"))
                .andDo(print())
                .andExpect(jsonPath("$.data.list.length()", is(2)));


    }

}