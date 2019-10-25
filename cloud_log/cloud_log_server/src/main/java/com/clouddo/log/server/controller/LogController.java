package com.clouddo.log.server.controller;

import com.cloudd.commons.auth.controller.AuthController;
import com.clouddo.commons.common.util.UUIDGenerator;
import com.clouddo.commons.common.util.message.Result;
import com.clouddo.log.common.model.LogModel;
import com.clouddo.log.server.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志服务层
 * @author zhongming
 * @since 3.0
 * 2018/5/31上午11:28
 */
@RestController
@RequestMapping("/log")
public class LogController extends AuthController {

    @Autowired
    private LogService logService;

    /**
     * 保存日志操作
     * @param logModel
     * @return
     */
    @RequestMapping("/saveLog")
    public Result saveLog(@RequestBody LogModel logModel) {
        try {
            if (StringUtils.isEmpty(logModel.getId())) {
                logModel.setId(UUIDGenerator.INSTANCE.getUUID());
            }
            logService.save(logModel);
            return Result.success("日志保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure("日志保存失败", e.getMessage());
        }
    }
}
