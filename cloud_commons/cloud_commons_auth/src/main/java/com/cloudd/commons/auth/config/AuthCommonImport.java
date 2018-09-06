package com.cloudd.commons.auth.config;

import com.clouddo.commons.common.imports.CommonAllImport;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 认证工具包配置扫描
 * 1、扫描类所在包和子包的配置信息
 * 2、引入common工具包配置
 * @author zhongming
 * @since 3.0
 * 2018/6/28下午4:07
 */
@Configuration
@ComponentScan
@Import({
        CommonAllImport.class
})
public class AuthCommonImport {

}
