package com.pdwu.easycloud.user.service.impl;

import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.common.util.UuidUtils;
import com.pdwu.easycloud.user.bean.TokenBean;
import com.pdwu.easycloud.user.constant.TokenConstant;
import com.pdwu.easycloud.user.dao.TokenDao;
import com.pdwu.easycloud.user.service.ITokenService;
import com.pdwu.easycloud.user.util.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TokenServiceImpl implements ITokenService {

    @Autowired
    private TokenDao tokenDao;

    @Transactional
    public TokenBean addToken(Long userId) {

        TokenBean bean = new TokenBean();
        bean.setUserId(userId);
        bean.setStatus(TokenConstant.STATUS_NORMAL);
        bean.setToken(UuidUtils.newUUID());
        Date date = new Date();
        bean.setCreateTime(date);
        bean.setLastTime(date);
        this.tokenDao.insertToken(bean);

        return bean;
    }

    @Transactional(readOnly = true)
    public ResultBean checkTokenValid(String token) {
        if (StringUtils.isBlank(token)) {
            return ResultBean.ARG_ERROR;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("token", token);
        map.put("status", TokenConstant.STATUS_NORMAL);
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

    @Transactional
    public ResultBean updateTokenStatus(String token, Integer status) {
        if (StringUtils.isBlank(token) || status == null) {
            return ResultBean.ARG_ERROR;
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("token", token);
        param.put("status", status);
        int updatedCount = this.tokenDao.updateTokenStatus(param);

        if (updatedCount == 0) {
            return ResultBean.fail("token不存在");
        }

        return ResultBean.success("");
    }
}
