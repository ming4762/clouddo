package com.clouddo.quartz.common.imports;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * quartz工具包引入类
 * @author zhongming
 * @since 3.0
 * 2018/8/27上午9:52
 */
@Configuration
@ComponentScan(basePackages = {
        "com.clouddo.quartz.common"
})
public class QuartzCommonImport {

}
