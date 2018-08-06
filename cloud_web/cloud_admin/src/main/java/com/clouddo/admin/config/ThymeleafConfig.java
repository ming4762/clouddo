package com.clouddo.admin.config;

import com.clouddo.admin.thymeleaf.CharsmingDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Thymeleaf配置类
 * @author zhongming
 * @since 3.0
 * 2018/7/20上午8:59
 */
@Configuration
public class ThymeleafConfig {


    @Bean
    public CharsmingDialect charsmingDialect() {
        return new CharsmingDialect();
    }
}
