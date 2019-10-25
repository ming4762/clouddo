package com.clouddo.auth.common.interceptor

import com.clouddo.auth.common.annotation.RequiresPermissions
import com.clouddo.auth.common.feign.AuthService
import org.apache.shiro.authz.annotation.Logical
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 用户鉴权服务
 * 获取需要的权限向鉴权服务发送请求
 * @author zhongming
 */
class UserAuthRestInterceptor: HandlerInterceptorAdapter() {

    companion object {
        private val logger = LoggerFactory.getLogger(UserAuthRestInterceptor :: class.java)
    }

    @Autowired
    private lateinit var authService: AuthService

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler is HandlerMethod) {
            // 获取访问方法上的权限注释
            val requiresPermissions = handler.getMethodAnnotation(RequiresPermissions :: class.java)
            // 注释不存在不拦截
            if (requiresPermissions != null) {
                // 获取权限集合
                val permissions = requiresPermissions.value
                if (permissions.isNotEmpty()) {
                    // 获取权限 Logical
                    val logical = requiresPermissions.logical.name
                    // 标识是否需要全部权限
                    var needAll = true
                    if (logical.equals(Logical.OR.name)) {
                        needAll = false
                    }
                    // 向鉴权服务发送请求进行鉴权
                    val result = this.authService.doAuthentication(mapOf("permissions" to permissions, "needAll" to needAll)).data
                    if (!result) {
                        logger.warn("当前用户没有权限访问，需要权限：{}【{}】", permissions, if (needAll) "全部拥有" else "拥有其中一项")
                        response.sendError(401, "当前用户没有权限访问")
                        return false
                    }
                }
            }
        }
        return super.preHandle(request, response, handler)
    }
}