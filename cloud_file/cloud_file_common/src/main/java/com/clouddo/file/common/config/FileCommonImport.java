package com.clouddo.file.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 文件工具包扫描类
 * @author zhongming
 * @since 3.0
 * 2018/7/24上午10:01
 */
@Configuration
@ComponentScan({
        "com.clouddo.file.common"
})
public class FileCommonImport {

}
