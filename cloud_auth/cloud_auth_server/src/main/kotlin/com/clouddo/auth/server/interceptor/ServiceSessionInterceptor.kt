package com.clouddo.auth.server.interceptor

import com.clouddo.auth.common.session.Session
import com.clouddo.auth.common.util.SessionUtil
import com.clouddo.auth.server.config.UserAuthConfig
import com.clouddo.auth.server.util.AuthUtil
import com.clouddo.commons.common.service.RedisService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.StringUtils
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import java.lang.Exception
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * session拦截器，负责session保存修改
 */
class ServiceSessionInterceptor: HandlerInterceptorAdapter() {

    /**
     * 用户认证配置信息
     */
    @Autowired
    private lateinit var userAuthConfig: UserAuthConfig

    @Autowired
    private lateinit var redisService: RedisService

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        //获取token
        val token = request.getHeader(userAuthConfig.tokenHeader)
        if (!StringUtils.isEmpty(token)) {
            //从redis中获取session信息
            val session = redisService.get(AuthUtil.createSessionId(token)) as Session
            SessionUtil.setUserSession(session)
        }
        return super.preHandle(request, response, handler)
    }

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {
        //保存session
        val session = SessionUtil.getUserSession()
        if (session != null) {
            this.redisService.put(AuthUtil.createSessionId(session.getId().toString()), session, session.getTimeout())
        }
        super.afterCompletion(request, response, handler, ex)
    }

}