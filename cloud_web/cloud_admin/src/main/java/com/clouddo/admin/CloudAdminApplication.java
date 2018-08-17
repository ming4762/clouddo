package com.clouddo.admin;

import com.clouddo.commons.common.imports.ApplicationContextImport;
import com.clouddo.commons.common.imports.RestUtilImport;
import com.clouddo.news.ui.config.NewUIImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * 启动类
 * 1、引入common工具包(引入ApplicationContextConfig配置、RestUtilConfig配置)
 * 2、引入news ui
 */
@SpringBootApplication
@Import({
		RestUtilImport.class,
		ApplicationContextImport.class,
		NewUIImport.class
})

public class CloudAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudAdminApplication.class, args);
	}
}
