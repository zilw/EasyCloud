package com.pdwu.easycloud.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by pdwu on 2017/12/14.
 */
public class JsonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String objectToJson(Object obj) {

        String res = "";

        try {
            res = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;

    }

}
