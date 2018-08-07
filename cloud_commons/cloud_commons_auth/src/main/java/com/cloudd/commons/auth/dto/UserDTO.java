package com.cloudd.commons.auth.dto;

import java.io.Serializable;

/**
 * @author zhongming
 * @since 3.0
 * 2018/8/7上午10:21
 */
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -8217514732430921176L;
    private String userId;
    // 用户名
    private String username;
    // 用户真实姓名
    private String name;

    private String mobile;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
