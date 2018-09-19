package com.clouddo.monitor.server.util;

import com.clouddo.commons.common.util.UUIDGenerator;
import com.clouddo.monitor.server.config.MonitorConfig;
import com.clouddo.monitor.server.constatns.MonitorConstatns;
import com.clouddo.monitor.server.model.CloudMonitorLog;
import com.clouddo.monitor.server.model.CloudServiceMonitor;
import com.clouddo.monitor.server.service.CloudMonitorLogService;
import com.clouddo.monitor.server.service.CloudServiceMonitorService;
import com.clouddo.quartz.common.model.CloudTimedTask;
import com.clouddo.quartz.common.service.QuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 监控工具类
 * 1、保存监测结果
 * 2、处理推送信息
 * @author zhongming
 * @since 3.0
 * 2018/8/30上午9:44
 */
@Component
public class MonitorUtil {

    /**
     * 存储失败的监控记录
     */
    public static Map<String, Map<String, Object>> monitorServerMap = new ConcurrentHashMap<String, Map<String, Object>>();

    private static final String ERROR_NUMBER = "errorNumber";
    private static final String NORMAL_NUMBER = "normalNumber";
    private static final String IS_PUSH_ERROR = "isPushError";

    @Autowired
    private MonitorConfig monitorConfig;
    @Autowired
    private CloudMonitorLogService cloudMonitorLogService;
    @Autowired
    private QuartzService quartzService;
    @Autowired
    private CloudServiceMonitorService cloudServiceMonitorService;

    @PostConstruct
    private void init() throws Exception {
        this.addRestPushJob();
    }

    /**
     * 添加重置推送定时任务
     */
    private void addRestPushJob() throws Exception {
        CloudTimedTask cloudTimedTask = new CloudTimedTask();
        cloudTimedTask.setCron(this.monitorConfig.getPush().getCron());
        cloudTimedTask.setClazz("com.clouddo.monitor.server.job.RestPushJob");
        cloudTimedTask.setTaskName("RestPushJob");
        cloudTimedTask.setTaskId("RestPushJob");
        quartzService.addTask(cloudTimedTask);
    }

    /**
     * 处理监测结果
     */
    public void handleMonitorResult(CloudServiceMonitor monitor, boolean status, String code, Long time, String message) throws Exception {
        // 达到多少次进行预警
        int limit = monitorConfig.getError().getLimit();
        // 保存日志
        this.createLog(monitor, status, code, time, message);
        if (!status) {
            this.handleError(monitor, limit);
        } else {
            this.handleNoraml(monitor);
        }
    }

    /**
     * 处理正常信息
     */
    private void handleNoraml(CloudServiceMonitor monitor) throws Exception {
        Map<String, Object> map = monitorServerMap.get(monitor.getServiceId());
        if(map != null) {
            map.put(ERROR_NUMBER, 0);
            map.put(NORMAL_NUMBER, (Integer)map.get(NORMAL_NUMBER) + 1);
            // 如果是错误后的第一次正常，修改状态为警告
            if((Integer)map.get(NORMAL_NUMBER) == 1) {
                monitor.setStatus(MonitorConstatns.WARNING.getValue());
                this.cloudServiceMonitorService.update(monitor);
            }
            // 如果正常次数达到限制，修改状态为正常
            if (this.monitorConfig.getError().getLimit().equals(map.get(NORMAL_NUMBER))) {
                monitor.setStatus(MonitorConstatns.NORMAL.getValue());
                this.cloudServiceMonitorService.update(monitor);
            }
        }
    }

    /**
     * 处理错误信息
     * @param monitor
     * @param limit
     */
    private void handleError(CloudServiceMonitor monitor, int limit) throws Exception {
        Map<String, Object> service = monitorServerMap.get(monitor.getServiceId());
        if (service == null) {
            service = new HashMap<String, Object>();
            service.put(ERROR_NUMBER, 1);
            service.put(IS_PUSH_ERROR, false);
            service.put(NORMAL_NUMBER, 0);
            monitorServerMap.put(monitor.getServiceId(), service);
        } else {
            Boolean isPushWarning = (Boolean) monitorServerMap.get(monitor.getServiceId()).get(IS_PUSH_ERROR);
            // 设置正常次数为0
            monitorServerMap.get(monitor.getServiceId()).put(NORMAL_NUMBER, 0);
            // 如果已经预警了 不在进行预警
            monitorServerMap.get(monitor.getServiceId()).put(ERROR_NUMBER, (Integer)monitorServerMap.get(monitor.getServiceId()).get(ERROR_NUMBER) + 1);
            if ((Integer)monitorServerMap.get(monitor.getServiceId()).get(ERROR_NUMBER) == limit) {
                if (!isPushWarning) {
                    // 进行预警
                    this.handlePushWarning(monitor);
                }
                // 标识为故障
                monitor.setStatus(MonitorConstatns.ERROR.getValue());
                this.cloudServiceMonitorService.update(monitor);
            }
        }
    }

    /**
     * 进行预警
     * TODO 待完善
     * @param monitor
     */
    private void handlePushWarning(CloudServiceMonitor monitor) {

        System.out.println("发送预警，" + monitor.getServiceName());
    }

    /**
     * 生成日志并保存
     * @param monitor
     * @param code
     * @param time
     * @param message
     * @return
     */
    private void createLog(CloudServiceMonitor monitor, boolean status, String code, Long time, String message) {
        CloudMonitorLog cloudMonitorLog = new CloudMonitorLog();
        cloudMonitorLog.setLogId(UUIDGenerator.getUUID());
        cloudMonitorLog.setServiceId(monitor.getServiceId());
        cloudMonitorLog.setStatus(status);
        cloudMonitorLog.setCode(code);
        cloudMonitorLog.setTime(new Date());
        cloudMonitorLog.setUseTime(time.intValue());
        cloudMonitorLog.setMessage(message);
        this.cloudMonitorLogService.insert(cloudMonitorLog);
    }
}
