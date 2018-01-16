package com.pdwu.easycloud.common.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by pdwu on 2018/1/16.
 */
public class MD5UtilsTest {

    @Test
    public void getStringMd5() {

        try {
            String s = MD5Utils.getStringMd5(null);
            fail();
        } catch (NullPointerException e) {

        }

        assertEquals("202cb962ac59075b964b07152d234b70", MD5Utils.getStringMd5("123"));

        assertEquals("900150983cd24fb0d6963f7d28e17f72", MD5Utils.getStringMd5("abc"));

    }

}