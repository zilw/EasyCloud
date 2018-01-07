package com.pdwu.easycloud.user.interceptor;

import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.common.bean.ResultCode;
import com.pdwu.easycloud.common.util.JsonUtils;
import com.pdwu.easycloud.user.bean.TokenBean;
import com.pdwu.easycloud.user.constant.TokenConstant;
import com.pdwu.easycloud.user.service.ITokenService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * Created by pdwu on 2017/12/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/resources")
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml", "classpath:spring/spring-mvc.xml"})
public class LoginInterceptorTest {

    private MockMvc mockMvc;

    @InjectMocks
    private LoginInterceptor loginInterceptor;

    @Mock
    private ITokenService tokenService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        // mockMvc = MockMvcBuilders.standaloneSetup(loginInterceptor).build();
    }

    @Test
    public void preHandle() throws Exception {

        //失效的token: token111
        Mockito.when(tokenService.checkTokenValid("token111")).thenReturn(ResultBean.fail(1, ""));
        //有效的token: token222
        Mockito.when(tokenService.checkTokenValid("token222")).thenAnswer(new Answer<ResultBean>() {
            public ResultBean answer(InvocationOnMock invocationOnMock) throws Throwable {
                TokenBean tokenBean = new TokenBean();
                tokenBean.setUserId(1233L);
                tokenBean.setToken("token222");
                return ResultBean.success(tokenBean);
            }
        });

        String pubUri = "/api/pub/xxx";//公共uri
        String apiUri = "/api/usr/xxx";//需要登录的uri

        //1.1 不带token访问需要登录的api     (禁止)
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest("GET", apiUri);

        assertEquals(false, loginInterceptor.preHandle(request, response, null));
        ResultBean resultBean11 = JsonUtils.jsonToResultBean(response.getContentAsString());
        assertEquals(ResultCode.unauthorized, resultBean11.getCode());


        //1.2 不带token访问公共api （通过）
        request.setRequestURI(pubUri);
        assertEquals(true, loginInterceptor.preHandle(request, response, null));

        //2.1 带合法token访问需要登录的api    （通过，并放入用户信息）
        MockHttpServletRequest request21 = new MockHttpServletRequest("GET", apiUri);
        MockHttpServletResponse response21 = new MockHttpServletResponse();
        request21.addHeader("Authorization", "token222");

        assertEquals(true, loginInterceptor.preHandle(request21, response21, null));
        assertEquals(1233L, request21.getSession().getAttribute("userId"));


        //2.2 带合法token访问公共api         （通过，并放入用户信息）
        MockHttpServletRequest request22 = new MockHttpServletRequest("GET", pubUri);
        MockHttpServletResponse response22 = new MockHttpServletResponse();
        request22.addHeader("Authorization", "token222");

        assertEquals(true, loginInterceptor.preHandle(request22, response22, null));
        assertEquals(1233L, request22.getSession().getAttribute("userId"));

        //3.1 带无效token访问需要登录的api    （禁止）
        MockHttpServletRequest request31 = new MockHttpServletRequest("GET", apiUri);
        MockHttpServletResponse response31 = new MockHttpServletResponse();
        request31.addHeader("authorization", "token111");   //大小写都ok

        assertEquals(false, loginInterceptor.preHandle(request31, response31, null));
        ResultBean resultBean31 = JsonUtils.jsonToResultBean(response31.getContentAsString());
        assertEquals(ResultCode.unauthorized, resultBean31.getCode());

        //3.2 带无效token访问公共api           （通过，没有用户信息）
        MockHttpServletRequest request32 = new MockHttpServletRequest("GET", pubUri);
        MockHttpServletResponse response32 = new MockHttpServletResponse();
        request32.addHeader("Authorization", "token111");

        assertEquals(true, loginInterceptor.preHandle(request32, response32, null));
        assertEquals(null, request32.getSession().getAttribute("userId"));

    }

    @Test
    public void postHandle() throws Exception {
        loginInterceptor.postHandle(null, null, null, null);
    }

    @Test
    public void afterCompletion() throws Exception {
        loginInterceptor.afterCompletion(null, null, null, null);

        new LoginInterceptor();
    }

    @Test
    public void getTokenFromRequest() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        //通过反射获取 getTokenFromRequest 方法
        Class<LoginInterceptor> interceptorClass = LoginInterceptor.class;
        Object instance = interceptorClass.newInstance();
        Method method = interceptorClass.getDeclaredMethod("getTokenFromRequest", HttpServletRequest.class);
        method.setAccessible(true);

        //没有token
        MockHttpServletRequest request = new MockHttpServletRequest();
        String token = (String) method.invoke(instance,request);
        assertEquals("", token);

        //测试token在header
        request.addHeader("Authorization", "token111");
        String s = (String) method.invoke(instance, request);
        assertEquals("token111", s);

        //测试空白token
        MockHttpServletRequest request1 = new MockHttpServletRequest();
        request1.addHeader("Authorization", "   ");
        String s1 = (String) method.invoke(instance, request1);
        assertEquals("", s1);

        //测试token在cookie
        MockHttpServletRequest request2 = new MockHttpServletRequest();
        Cookie cookie = new Cookie(TokenConstant.COOKIE_NAME, "tokenInCookie");
        request2.setCookies(cookie);

        String s2 = (String) method.invoke(instance, request2);
        assertEquals("tokenInCookie", s2);

        //测试存在cookie，但不存在token cookie
        MockHttpServletRequest request3 = new MockHttpServletRequest();
        Cookie cookie3 = new Cookie("hahaha", "tokenInCookie");
        request3.setCookies(cookie3);

        String s3 = (String) method.invoke(instance, request3);
        assertEquals("", s3);

    }


}