package com.pdwu.easycloud.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by pdwu on 2017/12/19.
 */
@Component
public class AppConfig {

    @Value("${app.file.userfilepath}")
    private String userFilePath;

    public String getUserFilePath() {
        return userFilePath;
    }

    public void setUserFilePath(String userFilePath) {
        this.userFilePath = userFilePath;
    }
}

