package com.clouddo.auth.common.util

import com.clouddo.auth.common.constatns.AuthConstants
import com.clouddo.auth.common.model.User

object UserUtil {

    /**
     * 获取当前用户
     */
    @JvmStatic fun getCurrentUser(): User? {
        val user = SessionUtil.getUserSession()?.getAttribute(AuthConstants.CONTEXT_KEY_USER)
        if (user is User) {
            return user
        }
        return null
    }
}