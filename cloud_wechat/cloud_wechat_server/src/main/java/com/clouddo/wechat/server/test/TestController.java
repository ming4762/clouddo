package com.clouddo.wechat.server.test;

import com.clouddo.commons.common.util.message.Result;
import com.clouddo.wechat.common.mp.config.WeChatMpConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhongming
 * @since 3.0
 * 2018/9/2下午8:09
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/test1")
    @ResponseBody
    public Result test1() {
        try {
            return Result.success(WeChatMpConfiguration.getDefaultService()
                    .getAccessToken());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.getMessage());
        }
    }
}
