package com.clouddo.monitor.server;

import com.clouddo.commons.common.imports.ApplicationContextImport;
import com.clouddo.commons.common.imports.RestUtilImport;
import com.clouddo.quartz.common.imports.QuartzCommonImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

/**
 * 监控启动类
 * 1、引入定时任务包 QuartzCommonImport
 * @author zhongming
 * @since 3.0
 */
@SpringBootApplication
@EnableEurekaClient
@Import({
		QuartzCommonImport.class,
		ApplicationContextImport.class,
		RestUtilImport.class
})
public class CloudMonitorServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudMonitorServerApplication.class, args);
	}
}
