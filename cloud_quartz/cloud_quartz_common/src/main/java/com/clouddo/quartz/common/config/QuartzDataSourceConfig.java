package com.clouddo.quartz.common.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 数据源配置
 * @author zhongming
 * @since 3.0
 * 2018/8/27上午9:55
 */
@Configuration
public class QuartzDataSourceConfig {
    /**
     * 系统库数据源
     * @return 数据源
     */
    @Bean(name = "quartzDataSource")
    @ConfigurationProperties("spring.datasource.druid.quartz")
    public DataSource dataSourceQuartz(){
        return DruidDataSourceBuilder.create().build();
    }
}
