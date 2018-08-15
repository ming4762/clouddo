package com.clouddo.commons.common.imports;

import com.clouddo.commons.common.config.ApplicationContextConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 项目上下文引入类
 * @author zhongming
 * @since 3.0
 * 2018/8/14上午9:37
 */
@Configuration
@Import({
        ApplicationContextConfig.class
})
public class ApplicationContextImport {

}
