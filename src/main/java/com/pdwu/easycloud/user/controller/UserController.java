package com.pdwu.easycloud.user.controller;

import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.common.config.AppConfig;
import com.pdwu.easycloud.user.bean.UserBean;
import com.pdwu.easycloud.user.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by pdwu on 2017/11/22.
 */
@Controller
public class UserController {

    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @RequestMapping(value = AppConfig.API_LOGIN, method = RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestBody Map<String, String> requestMap) {

        String account = requestMap.get("account");
        if (StringUtils.isBlank(account)) {
            return ResultBean.fail("账户不能为空");
        }
        String password = requestMap.get("password");
        if (StringUtils.isBlank(password)) {
            return ResultBean.fail("密码不能为空");
        }

        UserBean bean = userService.login(account, password);
        if (bean == null) {
            return ResultBean.fail("账户不存在或密码错误！");
        }

        return ResultBean.success(bean);
    }

    @RequestMapping(value = AppConfig.API_REGISTER, method = RequestMethod.POST)
    @ResponseBody
    public Object register(@RequestBody Map<String, String> requestMap) {

        log.debug("/register request: {}", requestMap.toString());


        if (StringUtils.isBlank(requestMap.get("account"))) {
            return ResultBean.fail("账户不能为空");
        }
        if (StringUtils.isBlank(requestMap.get("password"))) {
            return ResultBean.fail("密码不能为空");
        }
        return this.userService.register(requestMap.get("account"), requestMap.get("password"));
    }

    @RequestMapping(value = AppConfig.API_LOGOUT)
    @ResponseBody
    public Object logout(@RequestParam String token) {
        if (StringUtils.isBlank(token)) {
            return ResultBean.fail("注销失败，token不能为空");
        }

        return this.userService.logout(token);

    }

}
