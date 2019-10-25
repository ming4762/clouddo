package com.clouddo.auth.server.feigin.factory

import com.clouddo.auth.common.model.User
import com.clouddo.auth.server.feigin.UserService
import com.clouddo.commons.common.util.message.Result
import feign.hystrix.FallbackFactory
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class UserServiceFallbackFactory : FallbackFactory<UserService> {

    companion object {
        private val logger = LoggerFactory.getLogger(UserServiceFallbackFactory :: class.java)
    }

    override fun create(throwable: Throwable?): UserService {
        return object : UserService {
            override fun validate(parameters: Map<String, Any>): Result<User?> {
                logger.error("验证用户发生失败, 用户名：{}，密码：{}", parameters["username"], parameters["password"])
                return Result.failure(null)
            }

        }
    }
}