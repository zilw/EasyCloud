package com.pdwu.easycloud.common.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by pdwu on 2017/12/20.
 */
public class WebUtilsTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void checkUriPublic() throws Exception {
        new WebUtils();

        assertTrue(!WebUtils.checkUriPublic(""));

        assertTrue(WebUtils.checkUriPublic("/pub"));
        assertTrue(WebUtils.checkUriPublic("/pube"));
        assertTrue(WebUtils.checkUriPublic("/pub/asdf"));
        assertTrue(WebUtils.checkUriPublic("/pub/f/ff"));

    }

}