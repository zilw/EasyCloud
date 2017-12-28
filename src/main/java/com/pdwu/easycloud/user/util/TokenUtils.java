package com.pdwu.easycloud.user.util;

import com.pdwu.easycloud.user.bean.TokenBean;
import com.pdwu.easycloud.user.constant.TokenConstant;
import org.apache.commons.lang3.StringUtils;

public class TokenUtils {

    public static boolean isOverDue(TokenBean bean) {
        if (bean == null || StringUtils.isBlank(bean.getToken()) || bean.getCreateTime() == null) {
            return true;
        }

        // 与当前时间间隔
        long d = System.currentTimeMillis() - bean.getCreateTime().getTime();
        d = d / 1000;

        return d > TokenConstant.OVERDUE_TIME_SECOND;

    }

}
