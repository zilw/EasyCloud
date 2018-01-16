package com.pdwu.easycloud.common.util;

import org.springframework.util.DigestUtils;

/**
 * Created by pdwu on 2018/1/16.
 */
public class MD5Utils {

    public static String getStringMd5(String str) {

        //md5摘要（小写）
        String res = DigestUtils.md5DigestAsHex(str.getBytes());

        return res;
    }

}
