package com.pdwu.easycloud.user.interceptor;

import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.common.bean.ResultCode;
import com.pdwu.easycloud.common.bean.SessionAttributeConstant;
import com.pdwu.easycloud.common.util.JsonUtils;
import com.pdwu.easycloud.common.util.WebUtils;
import com.pdwu.easycloud.user.bean.TokenBean;
import com.pdwu.easycloud.user.constant.TokenConstant;
import com.pdwu.easycloud.user.service.ITokenService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private ITokenService tokenService;

    private Logger log = LoggerFactory.getLogger(getClass());

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        log.debug("preHandle.RequestURI: {}", httpServletRequest.getRequestURI());

        boolean isPublic = WebUtils.checkUriPublic(httpServletRequest.getRequestURI());

        String token = getTokenFromRequest(httpServletRequest);

        log.debug("preHandle.getTokenFromRequest.token: {}", token);


        //1. 不带token
        if (StringUtils.isBlank(token)) {
            //公共接口，可通过; 否则拦截
            return isPublic || returnTokenInvalid(httpServletRequest, httpServletResponse, o);
        }

        //2. 带了token，则认证
        ResultBean resultBean = tokenService.checkTokenValid(token);

        //2.1 token无效
        if (resultBean.getCode() != ResultCode.ok) {
            //公共接口，可通过; 否则拦截
            return isPublic || returnTokenInvalid(httpServletRequest, httpServletResponse, o);
        }

        //2.2 有效
        TokenBean tokenBean = (TokenBean) resultBean.getData();
        httpServletRequest.getSession().setAttribute("userId", tokenBean.getUserId());
        httpServletRequest.getSession().setAttribute(SessionAttributeConstant.TOKEN, token);

        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private String getTokenFromRequest(HttpServletRequest request) {

        String authorization = request.getHeader("Authorization");
        if (!StringUtils.isBlank(authorization)) {
            return authorization;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(TokenConstant.COOKIE_NAME)) {
                    return c.getValue();
                }
            }
        }


        return "";
    }

    private boolean returnTokenInvalid(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws IOException {

        httpServletResponse.setStatus(ResultCode.unauthorized);

        PrintWriter writer = httpServletResponse.getWriter();

        writer.write(JsonUtils.objectToJson(ResultBean.fail(ResultCode.unauthorized, "please login")));

        return false;

    }

}
