package com.pdwu.easycloud.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pdwu.easycloud.common.bean.ResultBean;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by pdwu on 2017/12/16.
 */
public class JsonUtilsTest {


    @Test
    public void objectToJson() throws Exception {

        String res = JsonUtils.objectToJson(ResultBean.fail(123, "fail"));
        assertEquals("{\"msg\":\"fail\",\"code\":123,\"data\":null}", res);

        String res2 = JsonUtils.objectToJson(ResultBean.success("hehe"));
        assertEquals("{\"msg\":\"\",\"code\":200,\"data\":\"hehe\"}", res2);

        new JsonUtils();
    }

    @Test
    public void jsonToResultBean() throws Exception {

        String s = "{\"msg\":\"hi\",\"data\":\"dddd\",\"code\":200}";
        ResultBean bean = JsonUtils.jsonToResultBean(s);
        assertEquals("hi", bean.getMsg());
        assertEquals("dddd", bean.getData());
        assertEquals(200, bean.getCode());

    }

    @Test
    public void test() throws IOException {

        for (int i = 0; i < 100; i++) {
            String res = JsonUtils.objectToJson(ResultBean.fail(123, "fail"));
            assertEquals("{\"msg\":\"fail\",\"code\":123,\"data\":null}", res);

            String s = "{\"msg\":\"hi\",\"data\":\"dddd\",\"code\":200}";
            ResultBean bean = JsonUtils.jsonToResultBean(s);
            assertEquals("hi", bean.getMsg());
            assertEquals("dddd", bean.getData());
            assertEquals(200, bean.getCode());

        }

    }

}