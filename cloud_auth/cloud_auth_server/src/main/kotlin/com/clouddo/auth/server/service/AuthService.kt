package com.clouddo.auth.server.service

import com.clouddo.auth.common.model.User

interface AuthService {

    /**
     * 添加登录人员
     */
    fun addLoginUser(user: User, token: String)

    /**
     * 刷新登录人员
     */
    fun refreshLoginUser(): Map<String, List<String>>
}