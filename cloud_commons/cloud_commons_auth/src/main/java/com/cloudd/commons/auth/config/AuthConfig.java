package com.cloudd.commons.auth.config;

import com.cloudd.commons.auth.util.TokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhongming
 * @since 3.0
 * 2018/6/28下午4:03
 */
@Configuration
public class AuthConfig {

    /**
     * 服务端认证配置信息
     * @return
     */
    @Bean
    public ServiceAuthConfig serviceAuthConfig() {
        return new ServiceAuthConfig();
    }

    /**
     * 用户认证配置信息
     * @return
     */
    @Bean
    public UserAuthConfig userAuthConfig() {
        return new UserAuthConfig();
    }

    @Bean
    public TokenUtil tokenUtil() {
        return new TokenUtil();
    }
}
