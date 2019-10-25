package com.clouddo.gateway

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.cloud.openfeign.EnableFeignClients


/**
 * 网关启动类
 * 引入认证包 AuthCommonImport.class
 * @author zhongming
 * @since 1.0
 * 2018-07-02 14:57
 */
@SpringBootApplication
@EnableEurekaClient  //服务提供者
@EnableZuulProxy //服务网关
@EnableFeignClients
class CloudGatewayApplication fun main (args: Array<String>){

    SpringApplication.run(CloudGatewayApplication::class.java, *args)
    println("ヾ(◍°∇°◍)ﾉﾞ    路由中心启动成功      ヾ(◍°∇°◍)ﾉﾞ\n" +
            " ______                    _   ______            \n" +
            "|_   _ \\                  / |_|_   _ `.          \n" +
            "  | |_) |   .--.    .--. `| |-' | | `. \\  .--.   \n" +
            "  |  __'. / .'`\\ \\/ .'`\\ \\| |   | |  | |/ .'`\\ \\ \n" +
            " _| |__) || \\__. || \\__. || |, _| |_.' /| \\__. | \n" +
            "|_______/  '.__.'  '.__.' \\__/|______.'  '.__.'  ")
}
