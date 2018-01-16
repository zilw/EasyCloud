package com.pdwu.easycloud.file.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by pdwu on 2018/1/16.
 */
public class FileSizeSerialize extends JsonSerializer<Long> {

    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {

        if (value == null) {
            gen.writeString("0B");
        } else {
            gen.writeString(FileSizeUtils.getStringSize(value));
        }

    }


}
