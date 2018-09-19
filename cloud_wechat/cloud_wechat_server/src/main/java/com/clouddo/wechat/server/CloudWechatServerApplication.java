package com.clouddo.wechat.server;

import com.clouddo.wechat.common.imports.WeChatMpImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

/**
 * 微信微服务启动类
 * 1、引入微信公众号工具包 WeChatMpImport.class
 * @author zhongming
 * @since 3.0
 */
@SpringBootApplication
@EnableEurekaClient
@Import({
		WeChatMpImport.class
})
public class CloudWechatServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudWechatServerApplication.class, args);
	}
}
