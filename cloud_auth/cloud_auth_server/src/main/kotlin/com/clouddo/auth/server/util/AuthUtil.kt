package com.clouddo.auth.server.util

import com.clouddo.auth.common.model.User
import com.clouddo.auth.common.session.Session
import com.clouddo.auth.common.session.impl.SimpleSession
import com.clouddo.auth.common.util.SessionUtil
import com.clouddo.auth.server.constatns.AuthConstants

object AuthUtil {

    /**
     * 创建sessionId
     */
    @JvmStatic fun createSessionId(token: String): String {
        return AuthConstants.SESSION_KEY_PREFIX + token
    }

    /**
     * 创建session
     */
    @JvmStatic fun createSession(user: User, token: String, type: String, timeout: Long) {
        val session: Session = SimpleSession(token, type)
        session.setTimeout(timeout)
        session.setAttribute(com.clouddo.auth.common.constatns.AuthConstants.CONTEXT_KEY_USER, user)
        SessionUtil.setUserSession(session)
    }
}