package com.pdwu.easycloud.file.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by pdwu on 2018/1/16.
 */
public class FileSizeUtilsTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getStringSize() throws Exception {

        String s = FileSizeUtils.getStringSize(1025);
        System.out.println(s);

        s = FileSizeUtils.getStringSize(100);
        System.out.println(s);

        s = FileSizeUtils.getStringSize(20490);
        System.out.println(s);

        s = FileSizeUtils.getStringSize(204900);
        System.out.println(s);

        s = FileSizeUtils.getStringSize(2049000);
        System.out.println(s);

        s = FileSizeUtils.getStringSize(6808055);
        System.out.println(s);

        s = FileSizeUtils.getStringSize(6808055000L);
        System.out.println(s);

        s = FileSizeUtils.getStringSize(6808055000000L);
        System.out.println(s);

        s = FileSizeUtils.getStringSize(6808055000000000L);
        System.out.println(s);

    }

}