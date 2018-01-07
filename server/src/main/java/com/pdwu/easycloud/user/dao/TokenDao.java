package com.pdwu.easycloud.user.dao;

import com.pdwu.easycloud.user.bean.TokenBean;

import java.util.List;
import java.util.Map;

public interface TokenDao {

    /**
     * 查找token
     * @param param (可指定token,userId,status, 并集)
     * @return
     */
    List<TokenBean> selectToken(Map param);

    int insertToken(TokenBean bean);

    int updateTokenStatus(Map param);

}
