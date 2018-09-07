package com.clouddo.system.controller;

import com.clouddo.commons.common.util.message.Result;
import com.clouddo.system.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhongming
 * @since 3.0
 * 2018/9/5下午1:27
 */
@Controller
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private ConfigService configService;

    @ResponseBody
    @RequestMapping("/local")
    public Result local() {
        try {
            return Result.success(this.configService.readConfig());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure("获取本地配置失败", e.getMessage());
        }
    }
}
