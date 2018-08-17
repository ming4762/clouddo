package com.clouddo.admin.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * 自定义错误页面
 * @author zhongming
 * @since 3.0
 * 2018/8/15下午3:38
 */
@Configuration
public class ErrorPageConfig implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/401");
        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404");
        ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500");

        registry.addErrorPages(error401Page, error404Page, error500Page);
    }
}
