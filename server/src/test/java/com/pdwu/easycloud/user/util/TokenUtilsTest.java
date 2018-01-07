package com.pdwu.easycloud.user.util;

import com.pdwu.easycloud.user.bean.TokenBean;
import com.pdwu.easycloud.user.constant.TokenConstant;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by pdwu on 2017/12/16.
 */
public class TokenUtilsTest {
    @Test
    public void isOverDue() throws Exception {

        //参数校验
        assertTrue(TokenUtils.isOverDue(null));
        assertTrue(TokenUtils.isOverDue(new TokenBean()));
        TokenBean t = new TokenBean();
        t.setToken("tttoken");
        t.setCreateTime(null);
        assertTrue(TokenUtils.isOverDue(t));


        TokenBean bean = new TokenBean();
        bean.setToken("oooo");

        //新token，未过期
        bean.setCreateTime(new Date());
        assertTrue(!TokenUtils.isOverDue(bean));

        //2个时间段前的token，已过期
        long temp = System.currentTimeMillis() - 2 * (TokenConstant.OVERDUE_TIME_SECOND * 1000);
        bean.setCreateTime(new Date(temp));
        assertTrue(TokenUtils.isOverDue(bean));

        //半个时间段前的token，未过期
        long temp2 = System.currentTimeMillis() - (TokenConstant.OVERDUE_TIME_SECOND * 500); //0.5*1000=500
        bean.setCreateTime(new Date(temp2));
        assertTrue(!TokenUtils.isOverDue(bean));

        new TokenUtils();
        new TokenConstant();

    }

}