package com.pdwu.easycloud.user.service;

import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.user.bean.UserBean;

import java.util.Map;

/**
 * Created by pdwu on 2017/12/9.
 */
public interface IUserService {

    UserBean login(String account, String password);

    ResultBean register(String account, String password);

    UserBean getUserInfo(Map<String,String> map);

}
