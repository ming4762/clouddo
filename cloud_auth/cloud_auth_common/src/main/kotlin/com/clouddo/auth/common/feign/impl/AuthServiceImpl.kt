package com.clouddo.auth.common.feign.impl

import com.clouddo.auth.common.feign.AuthService
import com.clouddo.auth.common.session.Session
import com.clouddo.commons.common.util.message.Result
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class AuthServiceImpl : AuthService {

    private val logger: Logger = LoggerFactory.getLogger(AuthServiceImpl :: class.java)


    /**
     * 获取session
     */
    override fun getSession(): Result<Session> {
        logger.error("获取session发生错误")
        return Result.failure("获取session发生错误")
    }

    /**
     * 保存session
     */
    override fun saveSession(session: Session): Result<Boolean> {
        logger.error("保存session发生错误")
        return Result.failure(false)
    }

    override fun doAuthentication(parameters: Map<String, Any>): Result<Boolean> {
        logger.error("鉴权发生错误")
        return Result.failure(false)
    }
}