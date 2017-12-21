package com.pdwu.easycloud.file.controller;

import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.file.service.IDownloadService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by pdwu on 2017/12/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/resources")
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml", "classpath:spring/spring-mvc.xml"})
public class DownloadControllerTest {

    @InjectMocks
    private DownloadController controller;

    @Mock
    private IDownloadService downloadService;

    private MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @Test
    public void download() throws Exception {

        Mockito.when(downloadService.download(1L, 1L, 1L, null))
                .thenReturn(ResultBean.fail(404, "notfound中文不乱码"));

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", 1L);

        mockMvc.perform(get("/download")
                .session(session)
                .param("fileId", "1")
                .param("shareId", "1"))
                .andDo(print())
                .andExpect(jsonPath("$.code", is(404)))
                .andExpect(jsonPath("$.msg", equalTo("notfound中文不乱码")));

        //TODO test success
    }

}