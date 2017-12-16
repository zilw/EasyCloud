package com.pdwu.easycloud.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdwu.easycloud.common.config.MyObjectMapper;

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

}
