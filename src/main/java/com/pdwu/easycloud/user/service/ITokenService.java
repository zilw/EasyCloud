package com.pdwu.easycloud.user.service;

import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.user.bean.TokenBean;

public interface ITokenService {

    TokenBean addToken(Long userId);

    ResultBean checkTokenValid(String token);

}
