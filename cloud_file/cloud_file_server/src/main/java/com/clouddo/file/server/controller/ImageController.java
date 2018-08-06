package com.clouddo.file.server.controller;

import com.cloudd.commons.auth.controller.AuthController;
import com.clouddo.commons.common.constatns.CommonConstants;
import com.clouddo.file.common.service.CloudFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * 图片接口
 * @author zhongming
 * @since 3.0
 * 2018/7/25下午5:07
 */
@Controller
@RequestMapping("/image")
public class ImageController extends AuthController {

    @Autowired
    private CloudFileService cloudFileService;

    /**
     * 图片显示接口
     * @param id 文件ID
     * @param width 图片压缩宽度
     * @param height 图片压缩高度
     * @param response
     */
    @GetMapping(value = "/{id}")
    public void show(@PathVariable("id") String id,
                     @RequestParam(value = "width", required = false) Integer width,
                     @RequestParam(value = "height", required = false) Integer height,
                     @RequestParam(value = CommonConstants.TOKEN_KEY, required = false) String token,
                     HttpServletResponse response) {
        try {
            //调用服务层接口显示图片
            this.cloudFileService.showImage(id, response.getOutputStream(), width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
