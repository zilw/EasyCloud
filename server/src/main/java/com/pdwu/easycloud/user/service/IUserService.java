package com.pdwu.easycloud.user.service;

import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.user.bean.UserBean;

import java.util.Map;

/**
 * Created by pdwu on 2017/12/9.
 */
public interface IUserService {

    /**
     * 登录
     *
     * @param account  账户
     * @param password 密码
     * @return
     */
    UserBean login(String account, String password);

    /**
     * 注册
     *
     * @param account  账户
     * @param password 密码
     * @return
     */
    ResultBean register(String account, String password);

    /**
     * 注销
     *
     * @param token
     * @return
     */
    ResultBean logout(String token);

    /**
     * 获取用户信息
     *
     * @param map 可传入 userId, account
     * @return
     */
    UserBean getUserInfo(Map<String, Object> map);

}
