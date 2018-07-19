package com.cloudd.commons.auth.util;

import com.cloudd.commons.auth.model.JWTUser;
import com.cloudd.commons.auth.model.User;
import com.clouddo.commons.common.constatns.CommonConstants;

/**
 * @author zhongming
 * @since 3.0
 * 2018/5/25上午10:33
 */
public class UserUtil {

    /**
     * 将IJWTInfo转为User
     * @param ijwtInfo
     * @return
     */
    public static User convertJwtToUser(JWTUser ijwtInfo) {
        User user = new User();
        user.setUserId(ijwtInfo.getUserId());
        user.setUsername(ijwtInfo.getUsername());
        user.setName(ijwtInfo.getName());
        return user;
    }

    /**
     * 将用户转为IJWTInfo
     * @param user
     * @return
     */
    public static JWTUser convertUserToJwt(User user) {
        JWTUser ijwtInfo = new JWTUser(user.getUsername(), user.getUserId().toString(), user.getName());
        return ijwtInfo;
    }

    /**
     * 获取当前登录用户
     * @return
     */
    public static User getCurrentUser() {
        return SessionUtil.getUserSession() == null ? null : (User) SessionUtil.getUserSession().getAttribute(CommonConstants.CONTEXT_KEY_USER);
    }
}
