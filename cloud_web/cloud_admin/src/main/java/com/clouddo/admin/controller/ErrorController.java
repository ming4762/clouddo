package com.clouddo.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhongming
 * @since 3.0
 * 2018/8/15下午3:58
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

    /**
     * 401错误页面
     * @return
     */
    @RequestMapping("/401")
    public ModelAndView error401() {
        return new ModelAndView("401");
    }
    @RequestMapping("/404")
    public ModelAndView error404() {
        return new ModelAndView("404");
    }
    @RequestMapping("/500")
    public ModelAndView error500() {
        return new ModelAndView("500");
    }
}
