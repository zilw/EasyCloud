package com.pdwu.easycloud.file.util;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.Assert.*;

/**
 * Created by pdwu on 2017/12/19.
 */
public class FileUtilsTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getFileMD5() throws Exception {
        new FileUtils();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("data", "filename.txt", "text/plain", "thisIsContent".getBytes());
        String res = FileUtils.getFileMD5(mockMultipartFile);
        assertEquals("f6c64c2816e3a17d19aae8fe1d709359", res);

        try {
            FileUtils.getFileMD5(null);
            fail(); //应有空指针异常
        } catch (Exception e) {

        }

    }

}