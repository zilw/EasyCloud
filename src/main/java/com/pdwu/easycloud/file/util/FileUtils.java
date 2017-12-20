package com.pdwu.easycloud.file.util;

import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by pdwu on 2017/12/19.
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    public static String getFileMD5(MultipartFile file) throws IOException {
        if (file == null) {
            throw new NullPointerException();
        }

        String digest = DigestUtils.md5DigestAsHex(file.getInputStream());

        return digest;
    }

}
