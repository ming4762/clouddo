package com.clouddo.monitor.server.job;

import com.clouddo.commons.common.util.http.HttpUtil;
import com.clouddo.monitor.server.model.CloudServiceMonitor;
import com.clouddo.monitor.server.util.MonitorUtil;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * 普通服务定制监测
 * @author zhongming
 * @since 3.0
 * 2018/8/28下午1:32
 */
@DisallowConcurrentExecution
public class NormalJob extends PublicJob {

    @Autowired
    private MonitorUtil monitorUtil;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        CloudServiceMonitor cloudServiceMonitor = this.getMonitorFromContext(context);
        if (cloudServiceMonitor != null) {
            Long beginTime = System.currentTimeMillis();
            String code = "";
            String message = "";
            boolean status = false;
            try {
                // 发送请求
                HttpResponse response = HttpUtil.httpGet(cloudServiceMonitor.getAddress(), null, null);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    status = true;
                }
                code = response.getStatusLine().getStatusCode() + "";
                message = response.getStatusLine().toString();
            } catch (IOException e) {
                message = e.getMessage();
                code = "error";
                e.printStackTrace();
            } finally {
                Long endTime = System.currentTimeMillis();
                try {
                    this.monitorUtil.handleMonitorResult(cloudServiceMonitor, status, code, endTime - beginTime, message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
