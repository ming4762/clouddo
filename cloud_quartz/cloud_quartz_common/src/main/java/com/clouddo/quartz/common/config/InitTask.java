package com.clouddo.quartz.common.config;

import com.clouddo.quartz.common.model.CloudTimedTask;
import com.clouddo.quartz.common.service.CloudTimedTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目启动时初始化任务
 * @author zhongming
 * @since 3.0
 * 2018/8/27下午3:09
 */
@Component
public class InitTask implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitTask.class);

    @Autowired
    CloudTimedTaskService cloudTimedTaskService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOGGER.debug("初始化定时任务");
        //获取所有任务
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("enable", true);
        List<CloudTimedTask> cloudTimedTaskList = cloudTimedTaskService.list(parameters);
        for (CloudTimedTask cloudTimedTask : cloudTimedTaskList) {
            this.cloudTimedTaskService.addTask(cloudTimedTask);
        }
    }
}
