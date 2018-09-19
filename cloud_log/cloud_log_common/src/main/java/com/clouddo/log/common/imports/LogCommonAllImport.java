package com.clouddo.log.common.imports;

import com.clouddo.log.common.config.LogConfig;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 日志工具包所有功能引入
 * @author zhongming
 * @since 3.0
 * 2018/8/15下午2:17
 */
@Configuration
@EnableFeignClients(basePackages = {
        "com.clouddo.log.common.feign"
})
@Import({
        LogConfig.class
})
public class LogCommonAllImport {

}
