package com.clouddo.quartz.common.service;

import com.clouddo.quartz.common.model.CloudTimedTask;
import org.quartz.JobDetail;

/**
 * Quartz服务类
 * @author zhongming
 * @since 3.0
 * 2018/8/27下午4:9
 */
public interface QuartzService {

    /**
     * 添加任务
     * @param cloudTimedTask
     * @return
     */
    void addTask(CloudTimedTask cloudTimedTask) throws Exception;

    /**
     * 移除任务
     * @param cloudTimedTask
     * @return
     */
    void removeTask(CloudTimedTask cloudTimedTask) throws Exception;

    /**
     * 重启任务
     * @param cloudTimedTask
     * @return
     */
    void resumeTask(CloudTimedTask cloudTimedTask) throws Exception;

    /**
     * 暂停任务
     * @param cloudTimedTask
     * @return
     */
    void pauseTask(CloudTimedTask cloudTimedTask) throws Exception;

    /**
     * 获取任务信息
     * @param cloudTimedTask
     * @return
     */
    JobDetail getJob(CloudTimedTask cloudTimedTask) throws Exception;
}
