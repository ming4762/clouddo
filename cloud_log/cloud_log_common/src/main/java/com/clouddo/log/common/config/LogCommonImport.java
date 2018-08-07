package com.clouddo.log.common.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 日志common工具包扫描类
 * @author zhongming
 * @since 3.0
 * 2018/6/28下午4:12
 */
@Configuration
@ComponentScan
@EnableFeignClients(basePackages = {
        "com.clouddo.log.common.feign"
})
public class LogCommonImport {

}
