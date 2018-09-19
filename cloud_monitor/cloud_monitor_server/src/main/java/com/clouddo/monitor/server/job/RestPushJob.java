package com.clouddo.monitor.server.job;

import com.clouddo.monitor.server.util.MonitorUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 重置推送job
 * @author zhongming
 * @since 3.0
 * 2018/8/30下午1:44
 */
public class RestPushJob extends PublicJob {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        MonitorUtil.monitorServerMap.clear();
    }
}
