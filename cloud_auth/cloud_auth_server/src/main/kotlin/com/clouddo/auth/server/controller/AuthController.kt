package com.clouddo.auth.server.controller

import com.clouddo.auth.common.session.Session
import com.clouddo.auth.common.util.SessionUtil
import com.clouddo.auth.common.util.UserUtil
import com.clouddo.auth.server.config.UserAuthConfig
import com.clouddo.auth.server.constatns.AuthConstants
import com.clouddo.auth.server.feigin.UserService
import com.clouddo.auth.server.model.JWTUser
import com.clouddo.auth.server.service.AuthService
import com.clouddo.auth.server.util.AuthUtil
import com.clouddo.auth.server.util.JWTHelper
import com.clouddo.commons.common.util.message.Result
import com.clouddo.commons.common.util.security.MD5Utils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping
class AuthController {

    companion object {
        private val logger = LoggerFactory.getLogger(AuthController :: class.java)
    }
    // 是否是开发模式
    @Value("\${auth.development:true}")
    private var development: Boolean = false
    /**
     * 用户认证配置信息
     */
    @Autowired
    private lateinit var userAuthConfig: UserAuthConfig

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var authService: AuthService

    /**
     * 用户鉴权
     */
    @PostMapping("/doAuthentication")
    fun doAuthentication(@RequestBody paramters: Map<String, Any>): Result<Boolean> {
        try {
            // 开发模式返回true
            if (this.development) {
                return Result.success(true)
            }
            // 获取用户所有权限
            val permissions = paramters["permissions"] as List<String>
            val needAll = paramters["needAll"] as Boolean
            val userPermissions = SessionUtil.getUserSession()?.getAttribute(AuthConstants.USER_PERMISSIONS) ?: return Result.success(false)
            if (needAll) {
                return Result.success(this.hasAllPermission(userPermissions as Set<String>, permissions))
            } else {
                return Result.success(this.hasOnePermission(userPermissions as Set<String>, permissions))
            }

        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(false)
        }
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    fun login(@RequestBody paramters: Map<String, String>): Result<Map<String, Any>> {
        // 获取用户名密码
        val username = paramters.get("username")
        val password = paramters["password"]
        return this.doLogin(username, password, Session.WEB_TYPE)
    }

    /**
     * 移动端登录
     */
    @PostMapping("/mobileLogin")
    fun mobileLogin(@RequestBody paramters: Map<String, String>): Result<Map<String, Any>> {
        // 获取用户名密码
        val username = paramters.get("username")
        val password = paramters["password"]
        return this.doLogin(username, password, Session.MOBILE_TYPE)
    }

    /**
     * 执行登录
     */
    private fun doLogin(username: String?, password: String?, type: String): Result<Map<String, Any>> {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            logger.warn("用户名或密码为空，username：{}  password：{}", username, password)
            return Result.failure(403, "用户名或密码不能为空")
        }
        val passwordEn = MD5Utils.encrypt(username, password)
        val parameters = mapOf<String, Any>("username" to username!!, "password" to passwordEn)
        val user = userService.validate(parameters).data
        // TODO 登录策略,限值IP、限值登录数
        if (user != null) {
            // 获取超时时间
            val timeout: Long = if (type == Session.WEB_TYPE) this.userAuthConfig.tokenExpire else this.userAuthConfig.mobileTokenExpire
            // 生成token
            val token = JWTHelper.generateToken(JWTUser(user.username!!, user.userId!!, user.name!!), this.userAuthConfig.priKeyPath, timeout)
            // 将人员添加到已登录信息中
            this.authService.addLoginUser(user, token)
            // 创建session
            AuthUtil.createSession(user, token, type, timeout)
            // 返回token
            logger.info("登陆成功，username：{}  password：{}", username, password)
            return Result.success(mapOf("token" to token))
        } else {
            logger.warn("用户名或密码错误，username：{}  password：{}", username, password)
            return Result.failure(403, "用户名或密码错误")
        }
    }

    /**
     * 验证用户是否登录
     */
    @PostMapping("/validateUser")
    fun validateUser(request: HttpServletRequest, @RequestBody parameters: Map<String, String>): Result<String?> {
        if (this.development) {
            return Result.success(null)
        }
        // 验证用户是否在不过滤的url
        val requestUrl = parameters["url"]
        // TODO  忽略的url
        var message = ""
        // 获取token
        val token = this.userAuthConfig.getToken(request)
        if (token == null) {
            return Result.success("请携带token")
        } else {
            val jwtUser: JWTUser
            try {
                jwtUser = JWTHelper.getInfoFromToken(token, userAuthConfig.pubKeyPath)
            } catch (e: Exception) {
                e.printStackTrace()
                logger.warn("token解密失败，token：{}", token)
                return Result.success("token解密失败，token：$token")
            }
            // 获取session
            val session = SessionUtil.getUserSession()
            if (session == null) {
                logger.warn("token认证失败，未找到session，token：{}", token)
                return Result.success("token认证失败，未找到session，token：$token")
            } else {
                // 验证session用户信息与token解密信息是否一致
                val userId = UserUtil.getCurrentUser()?.userId
                if (jwtUser.userId.equals(userId)) {
                    return Result.success(null)
                } else {
                    logger.warn("token认证失败，session信息与token解密信息不一致，token：{}", token)
                    return Result.success("token认证失败，session信息与token解密信息不一致，token：$token")
                }
            }
        }
    }

    /**
     * 获取session接口
     */
    @PostMapping("/session")
    fun session(request: HttpServletRequest): Result<Session?> {
        try {
            return Result.success(SessionUtil.getUserSession())
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e.message)
        }
    }

    /**
     * 保存session
     */
    @PostMapping("/saveSession")
    fun saveSession(@RequestBody request: HttpServletRequest, session: Session): Result<Boolean> {
        try {
            val token = this.userAuthConfig.getToken(request)
            if (StringUtils.isEmpty(token)) {
                return Result.success(false)
            } else {
                SessionUtil.setUserSession(session)
                return Result.success(true)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(false)
        }
    }

    /**
     * 是否拥有一项权限
     */
    private fun hasOnePermission(userPermissions: Set<String>, permissions: List<String>): Boolean {
        var result = false
        for (item in permissions) {
            if (userPermissions.contains(item)) {
                result = true
                break
            }
        }
        return result
    }

    /**
     * 是否拥有所有权限
     */
    private fun hasAllPermission(userPermissions: Set<String>, permissions: List<String>): Boolean {
        var result = true
        for(item in permissions) {
            if (!userPermissions.contains(item)) {
                result = false
                break
            }
        }
        return result
    }
}


