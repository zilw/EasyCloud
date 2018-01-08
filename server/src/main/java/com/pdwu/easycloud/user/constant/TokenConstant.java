package com.pdwu.easycloud.user.constant;

/**
 * Created by pdwu on 2017/12/16.
 */
public class TokenConstant {

    //token过期时间（单位：秒）
    public static final int OVERDUE_TIME_SECOND = 7200;

    public static final String COOKIE_NAME = "easycloud_token";


    //token状态： 正常
    public static final int STATUS_NORMAL = 0;

    //token状态： 删除/过期
    public static final int STATUS_DELETE = 1;

}
