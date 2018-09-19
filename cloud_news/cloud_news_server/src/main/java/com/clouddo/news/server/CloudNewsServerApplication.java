package com.clouddo.news.server;

import com.cloudd.commons.auth.config.AuthCommonImport;
import com.clouddo.log.common.imports.LogCommonAllImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

/**
 * 新闻服务启动类
 * @author zhongming
 */
@SpringBootApplication
@EnableEurekaClient   //服务提供者
@EnableFeignClients        //开启feign
@Import({
		//认证工具包引入
		AuthCommonImport.class,
		//引入日志工具包
		LogCommonAllImport.class
})
public class CloudNewsServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudNewsServerApplication.class, args);
	}
}
