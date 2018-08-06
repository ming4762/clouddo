package com.clouddo.news.ui.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 需要权限访问
 * @author zhongming
 * @since 3.0
 * 2018/7/30下午5:24
 */
@Controller
@RequestMapping("/news")
public class NewsWebController {

    /**
     * 默认的模块名
     */
    @Value("${cloud.config.news.moduleName}")
    private String defaultModuleName;

    /**
     * 跳转到新闻列表页面
     * @param parameters
     * @return
     */
    @RequestMapping("/newsList")
    public ModelAndView newsList(@RequestParam Map<String, Object> parameters) {
        //获取模块名称
        String moduleName = (String) parameters.get("moduleName");
        if(StringUtils.isEmpty(moduleName)) {
            moduleName = this.defaultModuleName;
            parameters.put("moduleName", moduleName);
        }
        return new ModelAndView("web/news/newsList", parameters);
    }

    /**
     * 跳转到添加添加新闻页面
     * @param parameters
     * @return
     */
    @RequestMapping("/newsAdd")
    public ModelAndView newsAdd(@RequestParam Map<String, Object> parameters) {
        return new ModelAndView("web/news/newsAdd", parameters);
    }

}
