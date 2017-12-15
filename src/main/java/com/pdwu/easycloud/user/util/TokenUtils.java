package com.pdwu.easycloud.user.util;

import com.pdwu.easycloud.user.bean.TokenBean;
import org.apache.commons.lang3.StringUtils;

public class TokenUtils {

    public static boolean isOverDue(TokenBean bean) {
        if (bean == null || StringUtils.isBlank(bean.getToken())) {
            return false;
        }

        //TODO
        return false;

    }

}
