package com.clouddo.admin.controller;

import com.clouddo.admin.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 公共controller，无需权限访问
 * @author zhongming
 * @since 3.0
 * 2018/7/17上午8:44
 */
@Controller
@RequestMapping("/web/public")
public class PublicWebController {

    @Autowired
    private AdminConfig adminConfig;

    /**
     * 跳转到登录页
     * @param parameters
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(Map<String, Object> parameters) {
        parameters.put("backgroudUrl", adminConfig.getBackgroudUrl());
        return new ModelAndView("admui/login", parameters);
    }
}
