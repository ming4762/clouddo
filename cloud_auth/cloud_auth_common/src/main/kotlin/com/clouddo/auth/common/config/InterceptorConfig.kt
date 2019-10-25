package com.clouddo.auth.common.config

import com.clouddo.auth.common.interceptor.SessionInterceptor
import com.clouddo.auth.common.interceptor.UserAuthRestInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class InterceptorConfig: WebMvcConfigurer {

    /**
     * session拦截器
     */
    @Bean
    fun sessionInterceptor() = SessionInterceptor()

    /**
     * 用户权限拦截器
     */
    @Bean
    fun userAuthRestInterceptor() = UserAuthRestInterceptor()

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(userAuthRestInterceptor())
        registry.addInterceptor(sessionInterceptor())
        super.addInterceptors(registry)
    }
}