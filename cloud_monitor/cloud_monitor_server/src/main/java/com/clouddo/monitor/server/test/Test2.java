package com.clouddo.monitor.server.test;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @author zhongming
 * @since 3.0
 * 2018/8/27下午3:03
 */
public class Test2 extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("这是测试2" + new Date());
    }
}
