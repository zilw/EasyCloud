package com.pdwu.easycloud.common.bean;

import org.junit.Test;

/**
 * Created by pdwu on 2017/12/16.
 */
public class BeanTest {

    @Test
    public void test() {
        new ResultCode();

        new ResultBean(1, "", "").toString();
    }

}
