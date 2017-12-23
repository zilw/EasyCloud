package com.pdwu.easycloud.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

/**
 * Created by pdwu on 2017/12/19.
 */
@Component
@PropertySource(value = "classpath:app/easycloud.properties")
public class AppConfig {
    /**
     *  api请求地址
     *
     *  /api/pub/xxx 公共API
     *  /api/usr/xxx 需要登录的API
     */

    //用户模块
    public static final String API_LOGIN = "/api/pub/login";
    public static final String API_LOGOUT = "/api/pub/logout";
    public static final String API_REGISTER = "/api/pub/register";

    //文件模块
    public static final String API_FILE = "/api/usr/file";
    public static final String API_FILE_UPLOAD = API_FILE + "/upload";
    public static final String API_FILE_LIST = API_FILE + "/list";
    public static final String API_FILE_RENAME = API_FILE + "/rename";
    public static final String API_FILE_DELETE = API_FILE + "/delete";
    public static final String API_FILE_SHARE = API_FILE + "/share";
    public static final String API_FILE_CANCEL_SHARE = API_FILE + "/cancelShare";

    //下载
    public static final String API_PUB_DOWNLOAD = "/api/pub/download";

    //分享信息
    public static final String API_PUB_SHARE = "/api/pub/share";

    //用户上传文件的保存地址
    @Value("${app.file.userfilepath}")
    private String userFilePath;

    public String getUserFilePath() {
        return userFilePath;
    }

    public void setUserFilePath(String userFilePath) {
        this.userFilePath = userFilePath;
    }

    //need this to resolve ${}, if use @PropertySource
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;
    }
}

