package com.clouddo.quartz.common.service.impl;

import com.clouddo.commons.common.util.ClassUtil;
import com.clouddo.quartz.common.model.CloudTimedTask;
import com.clouddo.quartz.common.service.QuartzService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

/**
 * Quartz服务类
 * @author zhongming
 * @since 3.0
 * 2018/8/27下午4:11
 */
@Service
public class QuartzServiceImpl implements QuartzService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    /**
     * 添加定时任务
     * @param cloudTimedTask
     * @throws Exception
     */
    @Override
    public void addTask(CloudTimedTask cloudTimedTask) throws Exception {
        //判断类是否继承自 QuartzJobBean
        if(ClassUtil.isAssignableFrom(cloudTimedTask.getClazz(), QuartzJobBean.class)) {
            Scheduler scheduler = this.schedulerFactoryBean.getScheduler();
            JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(cloudTimedTask.getClazz()))
                    .withIdentity(cloudTimedTask.getTaskId())
                    .build();
            jobDetail.getJobDataMap().put("task", cloudTimedTask);
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(cloudTimedTask.getTaskId())
                    .withSchedule(CronScheduleBuilder.cronSchedule(cloudTimedTask.getCron()))
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
        }
    }

    /**
     * 移除定时任务
     * @param cloudTimedTask
     * @throws Exception
     */
    @Override
    public void removeTask(CloudTimedTask cloudTimedTask) throws Exception {
        Scheduler scheduler = this.schedulerFactoryBean.getScheduler();
        scheduler.deleteJob(this.getJobKeyByCloudTimedTask(cloudTimedTask));
    }

    /**
     * 重启定时任务
     * @param cloudTimedTask
     * @throws Exception
     */
    @Override
    public void resumeTask(CloudTimedTask cloudTimedTask) throws Exception {
        Scheduler scheduler = this.schedulerFactoryBean.getScheduler();
        scheduler.resumeJob(this.getJobKeyByCloudTimedTask(cloudTimedTask));
    }

    /**
     * 暂停定时任务
     * @param cloudTimedTask
     * @throws Exception
     */
    @Override
    public void pauseTask(CloudTimedTask cloudTimedTask) throws Exception {
        Scheduler scheduler = this.schedulerFactoryBean.getScheduler();
        scheduler.pauseJob(this.getJobKeyByCloudTimedTask(cloudTimedTask));
    }

    /**
     * 获取任务信息
     * @param cloudTimedTask
     * @return
     */
    @Override
    public JobDetail getJob(CloudTimedTask cloudTimedTask) throws Exception {
        Scheduler scheduler = this.schedulerFactoryBean.getScheduler();
        return scheduler.getJobDetail(this.getJobKeyByCloudTimedTask(cloudTimedTask));
    }

    /**
     * 根据任务信息获取jobkey
     * @param cloudTimedTask
     * @return
     */
    private JobKey getJobKeyByCloudTimedTask(CloudTimedTask cloudTimedTask) {
        return JobKey.jobKey(cloudTimedTask.getTaskId());
    }

}
