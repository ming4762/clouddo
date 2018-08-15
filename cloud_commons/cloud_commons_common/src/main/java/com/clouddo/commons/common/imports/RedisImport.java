package com.clouddo.commons.common.imports;

import com.clouddo.commons.common.config.RedisConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * redis功能引入类
 * @author zhongming
 * @since 3.0
 * 2018/8/14上午9:35
 */
@Configuration
@Import({
        RedisConfig.class
})
public class RedisImport {

}
