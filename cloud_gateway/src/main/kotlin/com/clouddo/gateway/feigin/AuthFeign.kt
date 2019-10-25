package com.clouddo.gateway.feigin

import com.clouddo.commons.common.util.message.Result
import com.clouddo.gateway.feigin.impl.AuthFeignImpl
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody

@FeignClient(name = "cloud-auth-server", fallback = AuthFeignImpl :: class)
interface AuthFeign {

    @PostMapping("/validateUser")
    @ResponseBody
    fun validateUser(parameters: Map<String, String>): Result<String>
}