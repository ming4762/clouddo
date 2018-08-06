package com.clouddo.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
		"com.clouddo.ui",
		"com.clouddo.demo"
})
public class CloudUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudUiApplication.class, args);
	}
}
