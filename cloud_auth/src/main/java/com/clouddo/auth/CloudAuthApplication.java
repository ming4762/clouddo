package com.clouddo.auth;

import com.cloudd.commons.auth.config.AuthCommonImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

/**
 * 权限模块启动类
 * 1、功能引入{CommonAllImport common工具包}
 * @author zhongming
 */
@SpringBootApplication
@Import({
		AuthCommonImport.class,
//		LogCommonAllImport.class
})
@EnableFeignClients		//开启feign
@EnableEurekaClient   //服务提供者
public class CloudAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudAuthApplication.class, args);
		System.out.println("ヾ(◍°∇°◍)ﾉﾞ    认证中心启动成功      ヾ(◍°∇°◍)ﾉﾞ\n" +
				" ______                    _   ______            \n" +
				"|_   _ \\                  / |_|_   _ `.          \n" +
				"  | |_) |   .--.    .--. `| |-' | | `. \\  .--.   \n" +
				"  |  __'. / .'`\\ \\/ .'`\\ \\| |   | |  | |/ .'`\\ \\ \n" +
				" _| |__) || \\__. || \\__. || |, _| |_.' /| \\__. | \n" +
				"|_______/  '.__.'  '.__.' \\__/|______.'  '.__.'  ");
	}
}
