package com.pdwu.easycloud.user.controller;

import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.user.bean.UserBean;
import com.pdwu.easycloud.user.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by pdwu on 2017/11/22.
 */
@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestBody Map<String, String> requestMap) {

        String account = requestMap.get("username");
        if (StringUtils.isBlank(account)) {
            return ResultBean.fail("用户名不能为空");
        }
        String password = requestMap.get("password");
        if (StringUtils.isBlank(password)) {
            return ResultBean.fail("密码不能为空");
        }

        UserBean bean = userService.login(account, password);
        if (bean == null) {
            return ResultBean.fail("用户名不存在或密码错误！");
        }

        return ResultBean.success(bean);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Object register(@RequestBody UserBean bean) {

        if (StringUtils.isBlank(bean.getAccount())) {
            return ResultBean.fail("用户名不能为空");
        }
        if (StringUtils.isBlank(bean.getPassword())) {
            return ResultBean.fail("密码不能为空");
        }
        return this.userService.register(bean.getAccount(), bean.getPassword());
    }

}
