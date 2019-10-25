package com.clouddo.auth.common.feign.factory

import com.clouddo.auth.common.feign.AuthService
import com.clouddo.auth.common.session.Session
import com.clouddo.commons.common.util.message.Result
import feign.hystrix.FallbackFactory
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class AuthServiceFallbackFactory : FallbackFactory<AuthService> {

    companion object {
        private val logger = LoggerFactory.getLogger(AuthServiceFallbackFactory :: class.java)
    }

    override fun create(throwable: Throwable?): AuthService {
        return object : AuthService {
            override fun getSession(): Result<Session> {
                throwable?.printStackTrace()
                logger.error("获取session发生错误")
                return Result.failure("获取session发生错误")
            }

            override fun saveSession(session: Session): Result<Boolean> {
                throwable?.printStackTrace()
                logger.error("保存session发生错误")
                return Result.failure(false)
            }

            override fun doAuthentication(parameters: Map<String, Any>): Result<Boolean> {
//                throwable?.printStackTrace()
                logger.error("鉴权发生错误")
                return Result.failure(false)
            }

        }
    }
}