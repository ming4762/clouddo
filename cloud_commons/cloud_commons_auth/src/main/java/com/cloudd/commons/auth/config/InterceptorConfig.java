package com.cloudd.commons.auth.config;

import com.cloudd.commons.auth.interceptor.ServiceAuthRestInterceptor;
import com.cloudd.commons.auth.interceptor.SessionInterceptor;
import com.cloudd.commons.auth.interceptor.UserAuthRestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 * @author zhongming
 * @since 3.0
 * 2018/5/29下午1:50
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    //是否启用用户权限认证
    @Value("${auth.user.isUse:true}")
    private boolean isUserInterceptor;

    //是否启用客户端权限认证
    @Value("${auth.client.isUse:true}")
    private boolean isClientInterceptor;

    //用户权限认证拦截器
    @Bean
    public UserAuthRestInterceptor userAuthRestInterceptor() {
        return new UserAuthRestInterceptor();
    }

    /**
     * 服务端认证拦截器
     * @return
     */
    @Bean
    public ServiceAuthRestInterceptor serviceAuthRestInterceptor() {
        return new ServiceAuthRestInterceptor();
    }

    /**
     * session拦截器
     * @return
     */
    @Bean
    public SessionInterceptor sessionInterceptor() {
        return new SessionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor()).addPathPatterns("/**");
        if(isUserInterceptor) {
            registry.addInterceptor(userAuthRestInterceptor()).addPathPatterns("/**");
        }
        if(isClientInterceptor) {
            registry.addInterceptor(serviceAuthRestInterceptor()).addPathPatterns("/**");
        }
    }
}
