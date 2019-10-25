package com.clouddo.auth.server.feigin.impl

import com.clouddo.auth.common.model.User
import com.clouddo.auth.server.feigin.UserService
import com.clouddo.commons.common.util.message.Result
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class UserServiceImpl : UserService {

    companion object {
        private val logger = LoggerFactory.getLogger(UserServiceImpl :: class.java)
    }

    override fun validate(parameters: Map<String, Any>): Result<User?> {
        logger.error("验证用户发生失败, 用户名：{}，密码：{}", parameters.get("username"), parameters.get("password"))
        return Result.failure(null)
    }
}