package com.pdwu.easycloud.user.dao;

import com.pdwu.easycloud.user.bean.UserBean;

import java.util.List;
import java.util.Map;

/**
 * Created by pdwu on 2017/11/21.
 */
public interface UserDao {

    void insertUser(UserBean userBean);

    UserBean selectUserByAccountAndPassword(UserBean userBean);

    int selectUserAccountCount(String account);

    List<UserBean> selectUserInfo(Map<String, Object> map);

}
