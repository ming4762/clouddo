package com.clouddo.monitor.server.job;

import com.clouddo.monitor.server.model.CloudServiceMonitor;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * springcloud定时任务监测
 * @author zhongming
 * @since 3.0
 * 2018/8/28下午1:31
 */
@DisallowConcurrentExecution
public class SpringCloudJob extends PublicJob {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        CloudServiceMonitor cloudServiceMonitor = this.getMonitorFromContext(context);
    }
}
