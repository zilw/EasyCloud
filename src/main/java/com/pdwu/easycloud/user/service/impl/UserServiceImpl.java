package com.pdwu.easycloud.user.service.impl;

import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.common.bean.ResultCode;
import com.pdwu.easycloud.user.bean.TokenBean;
import com.pdwu.easycloud.user.bean.UserBean;
import com.pdwu.easycloud.user.dao.UserDao;
import com.pdwu.easycloud.user.service.ITokenService;
import com.pdwu.easycloud.user.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by pdwu on 2017/12/9.
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ITokenService tokenService;


    public UserBean login(String account, String password) {

        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return null;
        }

        UserBean arg = new UserBean();
        arg.setAccount(account);
        arg.setPassword(password);

        UserBean userBean = this.userDao.selectUserByAccountAndPassword(arg);

        if (userBean == null) {
            return null;
        }

        TokenBean tokenBean = tokenService.addToken(userBean.getUserId());
        userBean.setToken(tokenBean.getToken());

        return userBean;
    }

    public ResultBean register(String account, String password) {

        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return ResultBean.ARG_ERROR;
        }

        int count = this.userDao.selectUserAccountCount(account);
        if (count > 0) {
            return ResultBean.fail("账号已存在");
        }

        UserBean bean = new UserBean();
        bean.setAccount(account);
        bean.setPassword(password);
        Date date = new Date();
        bean.setCreateTime(date);
        bean.setLastTime(date);

        this.userDao.insertUser(bean);

        return ResultBean.success("注册成功");

    }

    public UserBean getUserInfo(Map<String, String> map) {
        if (map == null) return null;

        List<UserBean> list = this.userDao.selectUserInfo(map);
        if (list == null || list.size() == 0) {
            return null;
        }

        return list.get(0);
    }


}
