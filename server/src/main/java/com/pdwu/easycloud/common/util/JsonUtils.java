package com.pdwu.easycloud.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdwu.easycloud.common.bean.ResultBean;
import com.pdwu.easycloud.common.config.MyObjectMapper;

import java.io.IOException;

/**
 * Created by pdwu on 2017/12/14.
 */
public class JsonUtils {

    private static ObjectMapper objectMapper = new MyObjectMapper();

    public static String objectToJson(Object obj) throws JsonProcessingException {

        String res;
        res = objectMapper.writeValueAsString(obj);

        return res;

    }

    public static ResultBean jsonToResultBean(String string) throws IOException {
        return objectMapper.readValue(string, ResultBean.class);
    }

}
