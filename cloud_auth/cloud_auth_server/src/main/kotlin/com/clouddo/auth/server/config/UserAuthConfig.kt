package com.clouddo.auth.server.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import javax.servlet.http.HttpServletRequest

/**
 * 用户认证信息配置
 */
@Configuration
class UserAuthConfig {
    //用户公钥路径
    @Value("\${auth.user.pub-key.path}")
    lateinit var pubKeyPath: String

    @Value("\${auth.user.tokenExpire:3600}")
    var tokenExpire: Long = 3600

    //移动端token有效期 默认7天
    @Value("\${auth.user.mobileTokenExpire:604800}")
    var mobileTokenExpire: Long = 604800

    //token在请求头中的key
    @Value("\${auth.user.token-header}")
    lateinit var tokenHeader: String

    @Value("\${auth.user.pri-key.path:null}")
    lateinit var priKeyPath: String

    fun getToken(request: HttpServletRequest): String? {
        return request.getHeader(this.tokenHeader)
    }
}