package com.cloudd.commons.auth.controller;

import com.cloudd.commons.auth.model.User;
import com.cloudd.commons.auth.util.SessionUtil;
import com.clouddo.commons.common.constatns.CommonConstants;
import com.clouddo.commons.common.controller.BaseController;

/**
 * @author zhongming
 * @since 3.0
 * 2018/5/29下午5:13
 */
public class AuthController extends BaseController {

    /**
     * 获取当前用户
     * @return
     */
    public User getUser() {
        return SessionUtil.getUserSession() == null ? null : (User)SessionUtil.getUserSession().getAttribute(CommonConstants.CONTEXT_KEY_USER);
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
