package com.clouddo.monitor.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author zhongming
 * @since 3.0
 * 2018/8/28上午10:03
 */
@Controller
@RequestMapping("/monitor/web")
public class MonitorWebController {

    /**
     * 跳转到服务监控列表页面
     * @param parameters
     * @return
     */
    @RequestMapping("/serviceMonitor")
    public ModelAndView serviceMonitor(@RequestParam Map<String, Object> parameters) {
        return new ModelAndView("monitor/serviceMonitor/serviceMonitor", parameters);
    }

    /**
     * 跳转到服务监控添加修改页面
     * @param parameters
     * @return
     */
    @RequestMapping("/serviceMonitorAddEdit")
    public ModelAndView serviceMonitorAddEdit (@RequestParam Map<String, Object> parameters) {
        return new ModelAndView("monitor/serviceMonitor/serviceMonitorAddEdit", parameters);
    }
}
