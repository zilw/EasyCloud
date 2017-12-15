package com.pdwu.easycloud.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class MyObjectMapper extends ObjectMapper {

    public MyObjectMapper(){
        this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }
}
