package com.clouddo.log.server;

import com.cloudd.commons.auth.config.AuthCommonImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableEurekaClient        //服务提供者
@Import({
		AuthCommonImport.class
})
public class CloudLogServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudLogServerApplication.class, args);
	}
}
