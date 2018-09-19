package com.clouddo.monitor.server.job;

import com.clouddo.monitor.server.model.CloudServiceMonitor;
import com.clouddo.monitor.server.service.CloudServiceMonitorService;
import com.clouddo.quartz.common.model.CloudTimedTask;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author zhongming
 * @since 3.0
 * 2018/8/29下午4:46
 */
@DisallowConcurrentExecution
public class PublicJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

    }

    @Autowired
    private CloudServiceMonitorService cloudServiceMonitorService;

    /**
     * 从job上下文中获取监控服务信息
     * @param context
     * @return
     */
    protected CloudServiceMonitor getMonitorFromContext(JobExecutionContext context) {
        CloudTimedTask cloudTimedTask = (CloudTimedTask) context.getJobDetail().getJobDataMap().get("task");
        if (cloudTimedTask != null) {
            return this.cloudServiceMonitorService.get(cloudTimedTask.getTaskId());
        }
        return null;
    }
}
