package com.clouddo.auth.common.util

import com.clouddo.auth.common.session.Session

/**
 * session工具类
 */
object SessionUtil {

    private const val USER_TOKEN: String = "userToken"

    private val threadLocal: ThreadLocal<MutableMap<String, Session?>> = ThreadLocal.withInitial {
        mutableMapOf<String, Session?>()
    }

    /**
     * 获取用户session
     */
    @JvmStatic fun getUserSession() = threadLocal.get()[USER_TOKEN]

    /**
     * 设置session
     */
    @JvmStatic fun setUserSession(session: Session?) {
        threadLocal.get().put(USER_TOKEN, session)
    }

    /**
     * 删除session
     */
    @JvmStatic fun deleteSession() {
        threadLocal.remove()
    }

}