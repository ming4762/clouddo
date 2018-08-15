package com.clouddo.commons.common.imports;

import com.clouddo.commons.common.config.ApplicationContextConfig;
import com.clouddo.commons.common.config.RedisConfig;
import com.clouddo.commons.common.config.RestUtilConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * common工具包所有功能引入
 * @author zhongming
 * @since 3.0
 * 2018/8/14上午9:34
 */
@Configuration
@Import({
        ApplicationContextConfig.class,
        RedisConfig.class,
        RestUtilConfig.class
})
public class CommonAllImport {

}
