package com.clouddo.auth.server.constatns

/**
 * 认证常量信息
 * @author zhongming
 * @since 3.0
 * 2018/8/14上午10:44
 */
object AuthConstants {

    /**
     * TOKEN异常信息
     */
    val EX_TOKEN_ERROR_CODE = 40301

    const val TIMEOUT_KEY = "timeout"

    /**
     * token权限验证失败
     */
    val EX_TOKEN_AUTH_FAIL = 40305

    /**
     * 前后台传输token的key
     */
    val TOKEN_KEY = "Authorization"

    /**
     * session中存储用户权限的key
     */
    val USER_PERMISSIONS = "userPermissions"

    /**
     * redis session key前缀
     */
    val SESSION_KEY_PREFIX = "cloud_redis_session:"

    /**
     * 登录人员存储在redis的key
     */
    const val LOGIN_USERS_KEY = "auth_login_users"
}
