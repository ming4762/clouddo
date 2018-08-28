package com.clouddo.admin.controller;

import com.clouddo.admin.config.AdminConfig;
import com.clouddo.commons.common.constatns.CommonConstants;
import com.clouddo.commons.common.util.http.RestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * web页面跳转controller
 * @author zhongming
 * @since 3.0
 * 2018/7/11下午5:11
 */
@Controller
@RequestMapping("/web")
public class WebController {

    @Autowired
    private AdminConfig adminConfig;

    /**
     * 跳转到主页
     * @param parameters 参数信息
     * @return
     */
    @RequestMapping("/main")
    public ModelAndView main(Map<String, Object> parameters) {
        return new ModelAndView("main_v1", parameters);
    }

    /**
     * 跳转到菜单页面
     * @param parameters
     * @return
     */
    @RequestMapping("/system/menu")
    public ModelAndView menu(@RequestParam Map<String, Object> parameters) {
        return new ModelAndView("bootdo/menu/menu", parameters);
    }

    /**
     * 跳转到菜单页面
     * @param parameters
     * @return
     */
    @RequestMapping("/system/menu_v2")
    public ModelAndView menu2(@RequestParam Map<String, Object> parameters) {
        return new ModelAndView("bootdo/menu/menu_v2", parameters);
    }

    /**
     * 跳转到菜单添加修改页面
     * @param parameters
     * @return
     */
    @RequestMapping("/system/menuAddEdit")
    public ModelAndView menuAddEdit(@RequestParam Map<String, Object> parameters) {
        return new ModelAndView("bootdo/menu/menuAddEdit", parameters);
    }

    /**
     * 跳转到主页
     * @param parameters
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView index(@RequestParam Map<String, Object> parameters) {
        try {
            String token = (String) parameters.get(CommonConstants.TOKEN_KEY);
            Map<String, String> headers = new HashMap<String, String>();
            headers.put(CommonConstants.TOKEN_KEY, token);
            //获取登录信息
            ResponseEntity responseEntity = RestUtil.restPost(adminConfig.getBackgroudUrl() + "system/index", headers, "");
            Map<String, Object> data = (Map<String, Object>) responseEntity.getBody();
            return new ModelAndView("admui/index_v2", (Map<String, ?>) data.get("data"));
        } catch (Exception e) {
            return new ModelAndView("redirect:" + adminConfig.getLoginUrl());
        }
    }

    /**
     * 跳转到角色页面
     * @param parameters
     * @return
     */
    @RequestMapping("/system/role")
    public ModelAndView role(@RequestParam Map<String, Object> parameters) {
        return new ModelAndView("bootdo/role/role_v2", parameters);
    }

    /**
     * 跳转到到角色添加修改页面
     * @param parameters
     * @return
     */
    @RequestMapping("/system/roleAddEdit")
    public ModelAndView roleAddEdit(@RequestParam Map<String, Object> parameters) {
        return new ModelAndView("bootdo/role/roleAddEdit", parameters);
    }

    /**
     * 跳转到人员信息页面
     * @param parameters
     * @return
     */
    @RequestMapping("/system/user")
    public ModelAndView user(@RequestParam Map<String, Object> parameters) {
        return new ModelAndView("bootdo/user/user", parameters);
    }

    /**
     * 跳转到字典管理页面
     * @param parameters
     * @return
     */
    @RequestMapping("/system/dict")
    public ModelAndView dict(@RequestParam Map<String, Object> parameters) {
        return new ModelAndView("bootdo/dict/dict", parameters);
    }
}
