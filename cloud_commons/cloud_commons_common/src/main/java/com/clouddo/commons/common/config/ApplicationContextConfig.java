package com.clouddo.commons.common.config;

import com.clouddo.commons.common.service.ApplicationContextRegister;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhongming
 * @since 3.0
 * 2018/8/13上午10:28
 */
@Configuration
public class ApplicationContextConfig {

    /**
     * 获取spring上下文信息
     * @return
     */
    @Bean
    public ApplicationContextRegister applicationContextRegister() {
        return new ApplicationContextRegister();
    }
}
