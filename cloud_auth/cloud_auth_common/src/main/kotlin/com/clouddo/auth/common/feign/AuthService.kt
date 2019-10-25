package com.clouddo.auth.common.feign

import com.clouddo.auth.common.feign.impl.AuthServiceImpl
import com.clouddo.auth.common.session.Session
import com.clouddo.commons.common.util.message.Result
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody

@FeignClient(name = "cloud-auth-server", fallback = AuthServiceImpl :: class)
interface AuthService {

    companion object {
        const val DO_AUTHENTICATION_URL = "/auth/doAuthentication"
        const val GET_SESSION_URL = "/auth/session"
        const val SAVE_SESSION_URL = "/auth/saveSession"
    }

    /**
     * 获取session
     */
    @PostMapping(AuthService.GET_SESSION_URL)
    @ResponseBody
    fun getSession(): Result<Session>

    /**
     * 保存session
     */
    @PostMapping(AuthService.SAVE_SESSION_URL)
    @ResponseBody
    fun saveSession(session: Session): Result<Boolean>

    /**
     * 鉴权
     */
    @PostMapping(AuthService.DO_AUTHENTICATION_URL)
    @ResponseBody
    fun doAuthentication(@RequestBody parameters: Map<String, Any>): Result<Boolean>
}