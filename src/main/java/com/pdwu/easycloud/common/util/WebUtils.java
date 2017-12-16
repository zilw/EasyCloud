package com.pdwu.easycloud.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by pdwu on 2017/12/14.
 */
public class WebUtils {

    private static String[] pubUris = new String[]{
            "/pub/.*",
            "/error.*",
            "/login.*",
            "/register.*",
            "/static.*",
            "/share.*"
    };

    public static boolean checkUriPublic(String uri) {

        if (StringUtils.isBlank(uri)) {
            return false;
        }

        for (String s : pubUris) {
            if (uri.matches(s)) {
                return true;
            }
        }

        return false;
    }

}
