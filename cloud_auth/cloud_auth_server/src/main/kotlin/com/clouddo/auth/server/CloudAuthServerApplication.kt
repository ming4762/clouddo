package com.clouddo.auth.server

import com.clouddo.commons.common.imports.CommonAllImport
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Import

@SpringBootApplication
@EnableEurekaClient        //服务提供者
@EnableFeignClients
@Import(CommonAllImport::class)
class CloudAuthServerApplication fun main (args: Array<String>) {
        SpringApplication.run(CloudAuthServerApplication :: class.java, *args)
        println("ヾ(◍°∇°◍)ﾉﾞ    认证中心启动成功      ヾ(◍°∇°◍)ﾉﾞ\n" +
                " ______                    _   ______            \n" +
                "|_   _ \\                  / |_|_   _ `.          \n" +
                "  | |_) |   .--.    .--. `| |-' | | `. \\  .--.   \n" +
                "  |  __'. / .'`\\ \\/ .'`\\ \\| |   | |  | |/ .'`\\ \\ \n" +
                " _| |__) || \\__. || \\__. || |, _| |_.' /| \\__. | \n" +
                "|_______/  '.__.'  '.__.' \\__/|______.'  '.__.'  ")
}
