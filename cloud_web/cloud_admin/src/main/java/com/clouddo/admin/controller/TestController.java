package com.clouddo.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author zhongming
 * @since 3.0
 * 2018/9/9上午10:51
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/view")
    public ModelAndView testView(@RequestParam Map<String, Object> parameters) {
        return new ModelAndView("test/testView", parameters);
    }
}
