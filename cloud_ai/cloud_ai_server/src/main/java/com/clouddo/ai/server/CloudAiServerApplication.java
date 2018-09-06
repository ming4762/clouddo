package com.clouddo.ai.server;

import com.cloudd.commons.auth.config.AuthCommonImport;
import com.clouddo.log.common.imports.LogCommonAllImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;


/**
 * 引入认证工具包配置AuthCommonImport.class
 * 引入日志工具包配置LogCommonImport.class
 * EnableEurekaClient //服务提供者
 * @author zhongming
 * @since 1.0
 */
@SpringBootApplication
@EnableEurekaClient
@Import({
		AuthCommonImport.class,
		LogCommonAllImport.class
})
public class CloudAiServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudAiServerApplication.class, args);
		System.out.println("ヾ(◍°∇°◍)ﾉﾞ    AI服务模块启动成功      ヾ(◍°∇°◍)ﾉﾞ\n" +
				" ______                    _   ______            \n" +
				"|_   _ \\                  / |_|_   _ `.          \n" +
				"  | |_) |   .--.    .--. `| |-' | | `. \\  .--.   \n" +
				"  |  __'. / .'`\\ \\/ .'`\\ \\| |   | |  | |/ .'`\\ \\ \n" +
				" _| |__) || \\__. || \\__. || |, _| |_.' /| \\__. | \n" +
				"|_______/  '.__.'  '.__.' \\__/|______.'  '.__.'  ");
	}
}
