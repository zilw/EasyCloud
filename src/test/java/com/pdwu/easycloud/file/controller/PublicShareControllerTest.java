package com.pdwu.easycloud.file.controller;

import com.pdwu.easycloud.common.config.AppConfig;
import com.pdwu.easycloud.file.bean.ShareInfoBean;
import com.pdwu.easycloud.file.service.IShareService;
import com.pdwu.easycloud.file.service.IShortLinkService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by pdwu on 2017/12/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/resources")
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml", "classpath:spring/spring-mvc.xml"})
public class PublicShareControllerTest {

    @InjectMocks
    private PublicShareController controller;

    @Mock
    private IShareService shareService;
    @Mock
    private IShortLinkService shortLinkService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getShareFile() throws Exception {
        Mockito.when(shortLinkService.getShareId("shortlinkabc")).thenReturn(123L);
        Mockito.when(shortLinkService.getShareId("shortlinkefg")).thenReturn(456L);
        Mockito.when(shareService.getShareFileInfoById(123L)).thenReturn(null);
        Mockito.when(shareService.getShareFileInfoById(456L)).thenReturn(new ShareInfoBean());

        //文件已取消分享或不存在
        mockMvc.perform(get(AppConfig.API_PUB_SHARE + "/shortlinkabc"))
                .andDo(print())
                .andExpect(jsonPath("$.code", is(404)));

        //正常
        mockMvc.perform(get(AppConfig.API_PUB_SHARE + "/shortlinkefg"))
                .andDo(print())
                .andExpect(jsonPath("$.code", is(200)));

    }

}