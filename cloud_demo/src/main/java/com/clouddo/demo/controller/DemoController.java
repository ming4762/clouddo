package com.clouddo.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhongming
 * @since 3.0
 * 2018/7/9下午5:01
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping
    public ModelAndView demo() {
        return new ModelAndView("demo/demo");
    }
}
