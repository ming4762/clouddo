package com.clouddo.system;

import com.clouddo.auth.common.imports.AuthImport;
import com.clouddo.log.common.config.LogCommonImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

/**
 * 引入认证工具包配置AuthCommonImport.class
 * 引入日志工具包配置LogCommonImport.class
 * @author zhongming
 * @since 1.0
 */
@SpringBootApplication
@EnableEurekaClient		//服务提供者
@Import({
		AuthImport.class,
		LogCommonImport.class
})
public class CloudSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudSystemApplication.class, args);
		System.out.println("ヾ(◍°∇°◍)ﾉﾞ    系统模块启动成功      ヾ(◍°∇°◍)ﾉﾞ\n" +
				" ______                    _   ______            \n" +
				"|_   _ \\                  / |_|_   _ `.          \n" +
				"  | |_) |   .--.    .--. `| |-' | | `. \\  .--.   \n" +
				"  |  __'. / .'`\\ \\/ .'`\\ \\| |   | |  | |/ .'`\\ \\ \n" +
				" _| |__) || \\__. || \\__. || |, _| |_.' /| \\__. | \n" +
				"|_______/  '.__.'  '.__.' \\__/|______.'  '.__.'  ");
	}
}
