package com.clouddo.monitor.server.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @author zhongming
 * @since 3.0
 * 2018/8/28下午2:11
 */
@DisallowConcurrentExecution
public class SpringBootJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("springboot" + new Date());
        System.out.println(context.getJobDetail().getJobDataMap());
    }
}
