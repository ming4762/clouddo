package com.clouddo.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhongming
 * @since 3.0
 * 2018/7/19下午3:18
 */
@Component
public class AdminConfig {

    /**
     * 后台地址
     */
    @Value("${cloud.admin.setting.backgroudUrl}")
    private String backgroudUrl;

    /**
     * 登录地址
     */
    @Value("${cloud.admin.setting.loginUrl}")
    private String loginUrl;


    public String getBackgroudUrl() {
        return backgroudUrl;
    }

    public String getLoginUrl() {
        return loginUrl;
    }
}
