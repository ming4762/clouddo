package com.clouddo.quartz.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * quartz ui 跳转控制
 * @author zhongming
 * @since 3.0
 * 2018/8/27上午10:39
 */
@Controller
@RequestMapping("/quartz/web")
public class QuartzWebController {

    /**
     * 跳转到定时任务列表
     * @param parameters
     * @return
     */
    @RequestMapping("/timedTask")
    public ModelAndView timedTask(@RequestParam Map<String, Object> parameters) {
        return new ModelAndView("quartz/timedTask/timedTask", parameters);
    }

    /**
     * 跳转到定时任务添加修改页面
     * @param parameters
     * @return
     */
    @RequestMapping("/timedTaskAddEdit")
    public ModelAndView timedTaskAddEdit(@RequestParam Map<String, Object> parameters) {
        return new ModelAndView("quartz/timedTask/timedTaskAddEdit", parameters);
    }
}
