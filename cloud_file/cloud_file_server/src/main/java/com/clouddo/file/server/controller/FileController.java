package com.clouddo.file.server.controller;

import com.cloudd.commons.auth.controller.AuthController;
import com.clouddo.commons.common.util.message.Result;
import com.clouddo.file.common.dto.CloudFileDTO;
import com.clouddo.file.common.service.CloudFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传下载删除接口
 * @author zhongming
 * @since 3.0
 * 2018/7/25上午10:14
 */
@Controller
@RequestMapping("/file")
public class FileController extends AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private CloudFileService cloudFileService;

    /**
     * 单个文件上传接口
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public Object upload(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        try {
            return Result.success(this.cloudFileService.saveFile(multipartFile));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.getMessage());
        }
    }

    /**
     * 文件删除接口，要删除文件的ID
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public Object delete(@PathVariable("id") String id) {
        try {
            return Result.success(this.cloudFileService.deleteFile(id));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.getMessage());
        }
    }



    /**
     * 文件下载接口
     * @param id 文件ID
     * @param response
     */
    @GetMapping(value = "/{id}")
    public ModelAndView download(@PathVariable("id") String id, HttpServletResponse response) {
        Map<String, Object> modelData = new HashMap<String, Object>();
        try {
            //调用服务层接口下载文件
            CloudFileDTO cloudFileDTO = this.cloudFileService.download(id);
            if(cloudFileDTO != null && cloudFileDTO.getInputStream() != null) {
                //设置文件名并转码
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(cloudFileDTO.getCloudFileDO().getFileName(), "UTF-8"));
                //设置文件类型
                response.setContentType(cloudFileDTO.getCloudFileDO().getContentType());
                //写入文件流
                FileCopyUtils.copy(cloudFileDTO.getInputStream(), response.getOutputStream());
                return null;
            } else {
                LOGGER.warn("文件下载失败，未找到ID为{}的文件", id);
                modelData.put("message", "文件下载失败，未找到ID为【"+ id +"】的文件");
                return new ModelAndView("redirect:/error", modelData);
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("文件下载失败，发生未知错误，错误信息：[{}]", e.getMessage());
            modelData.put("message", "文件下载失败，发生未知错误，错误信息：" + e.getMessage());
            //跳转到异常页面
            return new ModelAndView("redirect:/error", modelData);
        }
    }
}
