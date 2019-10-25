package com.clouddo.auth.server.config

import com.clouddo.auth.server.interceptor.ServiceSessionInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * 拦截器配置
 */
@Configuration
class AuthServiceInterceptorConfig: WebMvcConfigurer {

    // session 拦截器
    @Bean
    fun serviceSessionInterceptor() = ServiceSessionInterceptor()

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(serviceSessionInterceptor()).addPathPatterns("/**")
        super.addInterceptors(registry)
    }
}