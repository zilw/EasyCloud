package com.pdwu.easycloud.common.util;

import java.util.UUID;

public class UuidUtils {

    public static String newUUID() {
        String uuid = UUID.randomUUID().toString();
        char[] chars = new char[32];
        for (int i = 0, j = 0; i < uuid.length(); i++) {
            if (uuid.charAt(i) != '-') {
                chars[j++] = uuid.charAt(i);
            }
        }
        return new String(chars);
    }

}
