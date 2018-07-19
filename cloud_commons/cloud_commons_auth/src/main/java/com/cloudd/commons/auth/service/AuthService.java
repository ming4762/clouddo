package com.cloudd.commons.auth.service;

import com.cloudd.commons.auth.model.User;
import com.cloudd.commons.auth.util.SessionUtil;
import com.clouddo.commons.common.constatns.CommonConstants;

/**
 * @author zhongming
 * @since 3.0
 * 2018/6/8下午4:37
 */
public class AuthService {

    /**
     * 获取当前用户
     * @return
     */
    public User getUser() {
        return SessionUtil.getUserSession() == null ? null : (User) SessionUtil.getUserSession().getAttribute(CommonConstants.CONTEXT_KEY_USER);
    }

    //获取用户名
    public String getUsername() {

        return getUser() == null ? null : getUser().getUsername();
    }

    //获取用户ID
    public String getUserId() {
        return getUser() == null ? null : getUser().getUserId();
    }
}
