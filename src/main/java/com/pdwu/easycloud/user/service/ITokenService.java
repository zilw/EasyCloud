package com.pdwu.easycloud.user.service;

import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.user.bean.TokenBean;

public interface ITokenService {

    /**
     * 为用户生成一个token
     *
     * @param userId
     * @return 新token
     */
    TokenBean addToken(Long userId);

    /**
     * 检查token是否有效
     *
     * @param token
     * @return code=1: 失效
     */
    ResultBean checkTokenValid(String token);

    /**
     * 更新token状态
     *
     * @param token
     * @param status
     * @return
     */
    ResultBean updateTokenStatus(String token, Integer status);

}
