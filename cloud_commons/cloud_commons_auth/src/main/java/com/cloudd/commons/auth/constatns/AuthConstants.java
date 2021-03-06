package com.cloudd.commons.auth.constatns;

/**
 * 认证常量信息
 * @author zhongming
 * @since 3.0
 * 2018/8/14上午10:44
 */
public class AuthConstants {

    /**
     * TOKEN异常信息
     */
    public static final Integer EX_TOKEN_ERROR_CODE = 40301;

    /**
     * token权限验证失败
     */
    public static final Integer EX_TOKEN_AUTH_FAIL = 40305;

    /**
     * 前后台传输token的key
     */
    public static final String TOKEN_KEY = "Authorization";

    /**
     * session中存储用户权限的key
     */
    public static final String USER_PERMISSIONS = "userPermissions";

    /**
     * redis session key前缀
     */
    public static final String SESSION_KEY_PREFIX = "cloud_redis_session:";
}
