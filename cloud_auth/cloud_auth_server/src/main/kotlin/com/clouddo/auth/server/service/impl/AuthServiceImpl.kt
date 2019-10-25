package com.clouddo.auth.server.service.impl

import com.clouddo.auth.common.model.User
import com.clouddo.auth.server.constatns.AuthConstants
import com.clouddo.auth.server.service.AuthService
import com.clouddo.commons.common.service.RedisService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * 认证服务层
 */
@Service
class AuthServiceImpl : AuthService {

    @Autowired
    private lateinit var redisService: RedisService

    /**
     * 刷新登录人员
     */
    override fun refreshLoginUser(): Map<String, List<String>> {
        // 获取所有token
        val tokenList = this.redisService.keys(AuthConstants.SESSION_KEY_PREFIX)
        var hasToken = true
        if (tokenList == null || tokenList.isEmpty()) {
            hasToken = false
        }
        val result = mutableMapOf<String, List<String>>()
        // 获取所有的登录人员信息
        val loginUserIdList = this.redisService.hashKeys(AuthConstants.LOGIN_USERS_KEY)
        loginUserIdList.forEach {
            if (!hasToken) {
                this.redisService.hashDelete(AuthConstants.LOGIN_USERS_KEY, it.toString())
            } else {
                val userTokenList: MutableList<String> = this.redisService.hashGet(AuthConstants.LOGIN_USERS_KEY, it.toString()) as MutableList<String>
                val newTokenList = mutableListOf<String>()
                userTokenList.forEach{token ->
                    if (tokenList.contains(token)) {
                        newTokenList.add(token)
                    }
                }
                if (newTokenList.isEmpty()) {
                    this.redisService.hashDelete(AuthConstants.LOGIN_USERS_KEY, it.toString())
                } else {
                    this.redisService.hashPut(AuthConstants.LOGIN_USERS_KEY, it.toString(), newTokenList)
                    result[it.toString()] = newTokenList
                }
            }
        }
        return result
    }

    /**
     * 添加登录人员
     */
    override fun addLoginUser(user: User, token: String) {
        var loginUserList = this.redisService.hashGet(AuthConstants.LOGIN_USERS_KEY, user.userId)
        if (loginUserList == null) {
            loginUserList = mutableListOf(token)
        } else {
            loginUserList as MutableList<String>
            loginUserList.add(token)
        }
        this.redisService.hashPut(AuthConstants.LOGIN_USERS_KEY, user.userId, loginUserList)
    }

}