package com.pdwu.easycloud.user.service.impl;

import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.common.util.UuidUtils;
import com.pdwu.easycloud.user.bean.TokenBean;
import com.pdwu.easycloud.user.dao.TokenDao;
import com.pdwu.easycloud.user.service.ITokenService;
import com.pdwu.easycloud.user.util.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TokenServiceImpl implements ITokenService {

    @Autowired
    private TokenDao tokenDao;

    public TokenBean addToken(Long userId) {

        TokenBean bean = new TokenBean();
        bean.setUserId(userId);
        bean.setStatus(0);
        bean.setToken(UuidUtils.newUUID());
        Date date = new Date();
        bean.setCreateTime(date);
        bean.setLastTime(date);
        this.tokenDao.insertToken(bean);

        return bean;
    }

    public ResultBean checkTokenValid(String token) {
        if (StringUtils.isBlank(token)) {
            return ResultBean.ARG_ERROR;
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("status", "0");
        List<TokenBean> list = this.tokenDao.selectToken(map);
        if (list == null || list.size() == 0) {
            return ResultBean.fail(1, "Token已失效");
        }

        boolean overdue = TokenUtils.isOverDue(list.get(0));
        if (overdue) {
            return ResultBean.fail(1, "Token已失效");
        }

        return ResultBean.success(list.get(0));
    }
}
