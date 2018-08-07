package com.clouddo.log.common.config;

import com.clouddo.log.common.aspect.LogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 日志common包配置类
 * EnableFeignClients 启动Feign支持
 * @author zhongming
 * @since 3.0
 * 2018/8/7下午2:39
 */
@Configuration
public class LogConfig {

    /**
     * 创建日志切面
     * @return
     */
    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }

}
