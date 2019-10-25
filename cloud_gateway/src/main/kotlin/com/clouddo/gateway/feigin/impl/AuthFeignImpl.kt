package com.clouddo.gateway.feigin.impl

import com.clouddo.commons.common.util.message.Result
import com.clouddo.gateway.feigin.AuthFeign
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class AuthFeignImpl : AuthFeign {

    companion object {
        private val logger = LoggerFactory.getLogger(AuthFeignImpl :: class.java)
    }

    override fun validateUser(parameters: Map<String, String>): Result<String> {
        logger.error("请求认证中心验证用户登录状态发生错误")
        return Result.failure("请求认证中心验证用户登录状态发生错误")
    }
}