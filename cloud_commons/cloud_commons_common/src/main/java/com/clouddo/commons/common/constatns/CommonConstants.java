package com.clouddo.commons.common.constatns;

/**
 * Created by ace on 2017/8/29.
 */
public class CommonConstants {
    public final static String RESOURCE_TYPE_MENU = "menu";
    public final static String RESOURCE_TYPE_BTN = "button";
    public static final Integer EX_TOKEN_ERROR_CODE = 40101;
    // 用户token异常
    public static final Integer EX_USER_INVALID_CODE = 40102;
    // 客户端token异常
    public static final Integer EX_CLIENT_INVALID_CODE = 40131;
    public static final Integer EX_CLIENT_FORBIDDEN_CODE = 40331;
    public static final Integer EX_OTHER_CODE = 500;
    public static final String CONTEXT_KEY_USER_ID = "currentUserId";
    public static final String CONTEXT_KEY_USERNAME = "currentUserName";
    //存储jwUser
    public static final String CONTEXT_KEY_JWTUSER = "currentJWTUser";

    //存储USER的key
    public static final String CONTEXT_KEY_USER = "currentUser";

    /**
     * 前后台传输token的key
     */
    public static final String TOKEN_KEY = "Authorization";

    public static final String CONTEXT_KEY_USER_TOKEN = "currentUserToken";
    public static final String JWT_KEY_USER_ID = "userId";
    public static final String JWT_KEY_NAME = "name";

    //session中存储用户权限的key
    public static final String USER_PERMISSIONS = "userPermissions";

    //部门根节点id
    public static Long DEPT_ROOT_ID = 0L;

    //演示系统账户
    public static String DEMO_ACCOUNT = "test";
}
