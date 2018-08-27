package com.clouddo.file.server;

import com.clouddo.file.common.config.FileCommonImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

/**
 * 文件微服务启动类
 * 1、引入文件工具包
 * @author zhongming
 * @since 3.0
 */
@SpringBootApplication
@EnableEurekaClient
@Import({
		FileCommonImport.class
})
public class CloudFileServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudFileServerApplication.class, args);
	}
}
