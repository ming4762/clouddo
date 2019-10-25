package com.clouddo.auth.common.interceptor

import com.clouddo.auth.common.feign.AuthService
import com.clouddo.auth.common.util.SessionUtil
import com.clouddo.commons.common.util.message.ResultCodeEnum
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * session拦截器
 * 为每个服务设置session
 */
class SessionInterceptor: HandlerInterceptorAdapter() {

    @Autowired
    private lateinit var authService: AuthService

    /**
     * 请求前获取session
     */
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        // 获取token
        val result = this.authService.getSession()
        if (result.code == ResultCodeEnum.SUCCESS.code && result.data != null) {
            SessionUtil.setUserSession(result.data)
        }
        SessionUtil.setUserSession(null)
        return super.preHandle(request, response, handler)
    }

    /**
     * 请求后保存session
     */
    override fun afterConcurrentHandlingStarted(request: HttpServletRequest, response: HttpServletResponse, handler: Any) {
        val session = SessionUtil.getUserSession()
        if (session != null) {
            this.authService.saveSession(session)
        }
        super.afterConcurrentHandlingStarted(request, response, handler)
    }
}