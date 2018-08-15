package com.clouddo.commons.common.imports;

import com.clouddo.commons.common.config.RestUtilConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * rest工具类引入类
 * @author zhongming
 * @since 3.0
 * 2018/8/14上午9:36
 */
@Configuration
@Import({
        RestUtilConfig.class
})
public class RestUtilImport {

}
