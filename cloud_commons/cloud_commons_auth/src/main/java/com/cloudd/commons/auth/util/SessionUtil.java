package com.cloudd.commons.auth.util;


import com.cloudd.commons.auth.constatns.AuthConstants;
import com.cloudd.commons.auth.model.User;
import com.cloudd.commons.auth.session.Session;
import com.cloudd.commons.auth.session.impl.SimpleSession;
import com.clouddo.commons.common.constatns.CommonConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhongming
 * @since 3.0
 * 2018/5/24下午1:57
 */
public class SessionUtil {

    private static final String SERVER_TOKEN = "serverToken";

    private static final String USER_TOKEN = "userToken";

    private static ThreadLocal<Map<String, Session>> threadLocal = ThreadLocal.withInitial(() -> {
                return new HashMap<String, Session>();
            }
    );

    /**
     * 设置服务端session
     */
    public static void setServerSession(Session session) {
        threadLocal.get().put(SERVER_TOKEN, session);
    }

    /**
     * 设置用户session
     */
    public static void setUserSession(Session session) {
        threadLocal.get().put(USER_TOKEN, session);
    }

    /**
     * 获取用户session
     * @return
     */
    public static Session getUserSession() {
        return threadLocal.get().get(USER_TOKEN);
    }

    /**
     * 获取服务端session
     * @return
     */
    public static Session getServiceSession() {
        return threadLocal.get().get(SERVER_TOKEN);
    }

    /**
     * 通过用户信息和token创建session
     * @param user
     * @param token
     */
    public static void createSession(User user, String token) {
        Session session = new SimpleSession(token);
        session.setAttribute(CommonConstants.CONTEXT_KEY_USER, user);
        session.setAttribute(CommonConstants.CONTEXT_KEY_JWTUSER, UserUtil.convertUserToJwt(user));
        setUserSession(session);
    }

    /**
     * 获取sessionId
     * @param id
     * @return
     */
    public static String getSessionId(String token) {
        return AuthConstants.SESSION_KEY_PREFIX + token;
    }

}
