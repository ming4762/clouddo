package com.clouddo.auth.common.imports

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 * 认证工具包配置扫描
 * @author zhongming
 */
@Configuration
@ComponentScan(
        "com.clouddo.auth.common.feign",
        "com.clouddo.auth.common.config"
)
@EnableFeignClients(basePackages = ["com.clouddo.auth.common.feign"])
class AuthImport {
}