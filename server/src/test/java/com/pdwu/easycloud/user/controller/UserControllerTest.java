package com.pdwu.easycloud.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.common.bean.SessionAttributeConstant;
import com.pdwu.easycloud.common.config.AppConfig;
import com.pdwu.easycloud.common.util.JsonUtils;
import com.pdwu.easycloud.user.bean.UserBean;
import com.pdwu.easycloud.user.service.IUserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by pdwu on 2017/12/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/resources")
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml", "classpath:spring/spring-mvc.xml"})
public class UserControllerTest {


    @InjectMocks
    private UserController userController;

    @Mock
    private IUserService userService;

    //@Autowired
    //private WebApplicationContext wac;
    private MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        //mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .build();
    }

    @Test
    public void login() throws Exception {

        //mock
        Mockito.when(userService.login("xiaoming", "xiaomingPsd")).thenAnswer(new Answer<UserBean>() {
            public UserBean answer(InvocationOnMock invocationOnMock) throws Throwable {
                UserBean userBean = new UserBean();
                userBean.setAccount("xiaoming");
                userBean.setPassword(null);
                userBean.setCreateTime(new Date(1513317967850L));
                userBean.setLastTime(new Date(1513317967850L));
                userBean.setToken("12d8aa790b4444aa9796c92cf8af28d9");

                return userBean;
            }
        });

        //发起请求 登录成功
        mockMvc.perform(getPostRequestWithAccountAndPassword(AppConfig.API_LOGIN, "xiaoming", "xiaomingPsd"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.account", equalTo("xiaoming")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.token", equalTo("12d8aa790b4444aa9796c92cf8af28d9")));
        //.andExpect(MockMvcResultMatchers.jsonPath("$.data.createTime", is(1513317967850L)))

        //密码错误
        mockMvc.perform(getPostRequestWithAccountAndPassword(AppConfig.API_LOGIN, "xiaoming", "errorPsd"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", is(400)));

        //用户名/密码为空
        mockMvc.perform(getPostRequestWithAccountAndPassword(AppConfig.API_LOGIN, "", ""))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", is(400)));
        mockMvc.perform(getPostRequestWithAccountAndPassword(AppConfig.API_LOGIN, "oooo", ""))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", is(400)));

    }

    @Test
    public void register() throws Exception {
        //注册失败
        Mockito.when(userService.register("zhangsan", "zhansanPsd")).thenReturn(ResultBean.fail("账号已存在"));

        mockMvc.perform(getPostRequestWithAccountAndPassword(AppConfig.API_REGISTER, "zhangsan", "zhansanPsd"))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg", equalTo("账号已存在")));


        //注册成功
        Mockito.when(userService.register("lisi", "lisiPsd")).thenReturn(ResultBean.success("注册成功"));
        mockMvc.perform(getPostRequestWithAccountAndPassword(AppConfig.API_REGISTER, "lisi", "lisiPsd"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", equalTo("注册成功")));

        //用户名/密码为空
        mockMvc.perform(getPostRequestWithAccountAndPassword(AppConfig.API_REGISTER, "", ""))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", is(400)));
        mockMvc.perform(getPostRequestWithAccountAndPassword(AppConfig.API_REGISTER, "faf", ""))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", is(400)));

    }

    @Test
    public void logout() throws Exception {
        Mockito.when(userService.logout("token1233")).thenReturn(ResultBean.success(""));

        //param 指定token
        mockMvc.perform(MockMvcRequestBuilders.get(AppConfig.API_LOGOUT).param("token", "token1233"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", is(200)));

        //从session中取token
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionAttributeConstant.TOKEN, "token1233");
        mockMvc.perform(MockMvcRequestBuilders.get(AppConfig.API_LOGOUT).session(session))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", is(200)));
    }

    //构建post json请求
    private RequestBuilder getPostRequestWithAccountAndPassword(String uri, String account, String password) throws JsonProcessingException {
        Map<String, String> param = new HashMap<String, String>();
        param.put("account", account);
        param.put("password", password);
        RequestBuilder request = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.objectToJson(param));

        return request;
    }

}