package com.pdwu.easycloud.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

/**
 * Created by pdwu on 2017/12/19.
 */
@Component
@PropertySource(value = {"classpath:app/easycloud.properties", "file:/home/myAppConf/easycloud.app.properties"}, ignoreResourceNotFound = true)
public class AppConfig {

    //应用部署地址/域名
    @Value("${app.common.site}")
    public String appSite;

    //短链接前缀 eg: example.com/share/abcd
    public static final String URL_SHORT_LINK_PRE = "share/";

    //用户上传文件的保存地址
    @Value("${app.file.userfilepath}")
    private String userFilePath;

    /**
     * api请求地址
     * <p>
     * /api/pub/xxx 公共API
     * /api/usr/xxx 需要登录的API
     */

    //用户模块
    public static final String API_LOGIN = "/api/pub/login";
    public static final String API_LOGOUT = "/api/usr/logout";
    public static final String API_REGISTER = "/api/pub/register";

    //文件模块
    public static final String API_FILE = "/api/usr/file";
    public static final String API_FILE_UPLOAD = API_FILE + "/upload";
    public static final String API_FILE_LIST = API_FILE + "/list";
    public static final String API_FILE_RENAME = API_FILE + "/rename";
    public static final String API_FILE_DELETE = API_FILE + "/delete";
    public static final String API_FILE_SHARE = API_FILE + "/share";
    public static final String API_FILE_CANCEL_SHARE = API_FILE + "/cancelShare";
    public static final String API_FILE_SHARE_LIST = API_FILE + "/shareList";

    //下载
    public static final String API_PUB_DOWNLOAD = "/api/pub/download";
    //预览
    public static final String API_PUB_PREVIEW = "/api/pub/preview";

    //分享信息
    public static final String API_PUB_SHARE = "/api/pub/share";

    public String getUserFilePath() {
        return userFilePath;
    }

    public void setUserFilePath(String userFilePath) {
        this.userFilePath = userFilePath;
    }

    public String getAppSite() {
        return appSite;
    }

    public void setAppSite(String appSite) {
        this.appSite = appSite;
    }

    //need this to resolve ${}, if use @PropertySource
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;
    }
}

