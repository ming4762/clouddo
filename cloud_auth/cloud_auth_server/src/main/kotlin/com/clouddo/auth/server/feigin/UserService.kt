package com.clouddo.auth.server.feigin

import com.clouddo.auth.common.model.User
import com.clouddo.auth.server.feigin.impl.UserServiceImpl
import com.clouddo.commons.common.util.message.Result
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@FeignClient(name = "cloud-system", fallback = UserServiceImpl :: class)
interface UserService {

    /**
     * 验证用户
     */
    @ResponseBody
    @RequestMapping("/sys/user/validate")
    fun validate(parameters: Map<String, Any>): Result<User?>
}