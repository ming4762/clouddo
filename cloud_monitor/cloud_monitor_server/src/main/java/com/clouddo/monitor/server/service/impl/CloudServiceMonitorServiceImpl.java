package com.clouddo.monitor.server.service.impl;


import com.clouddo.commons.common.util.UUIDGenerator;
import com.clouddo.monitor.server.mapper.CloudServiceMonitorMapper;
import com.clouddo.monitor.server.model.CloudServiceMonitor;
import com.clouddo.monitor.server.service.CloudServiceMonitorService;
import com.clouddo.quartz.common.model.CloudTimedTask;
import com.clouddo.quartz.common.service.CloudTimedTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Service层 实现类，用于业务逻辑处理，事务控制等 
 * @author charsmingCodeGenerator 
 */
@Service
public class CloudServiceMonitorServiceImpl implements CloudServiceMonitorService {
	
	@Resource
	private CloudServiceMonitorMapper cloudServiceMonitorMapper;

	@Autowired
	private CloudTimedTaskService timedTaskService;

	private static Map<String, String> monitorClass = new HashMap<>();

	@PostConstruct
    private void init() {
        monitorClass.put("SpringCloud", "com.clouddo.monitor.server.job.SpringCloudJob");
        monitorClass.put("SpringBoot", "com.clouddo.monitor.server.job.SpringBootJob");
        monitorClass.put("interface", "com.clouddo.monitor.server.job.InterfaceJob");
        monitorClass.put("normal", "com.clouddo.monitor.server.job.NormalJob");
    }

    /**
     * 查询操作
     * @param parameterSet 参数
     * @return 查询结果
     */
    @Override
    public List<CloudServiceMonitor> list(Map<String, Object> parameterSet) {
        return this.cloudServiceMonitorMapper.list(parameterSet);
    }

    /**
    * 删除操作
    * @param deleteObjects 要删除的实体对象集合，内含对象主键信息
    */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int delete(List<CloudServiceMonitor> deleteObjects) throws Exception {
        List<CloudTimedTask> cloudTimedTaskList = this.batchConvertMonitorToTask(deleteObjects);
        this.timedTaskService.delete(cloudTimedTaskList);
        return this.cloudServiceMonitorMapper.delete(deleteObjects);
    }

    /**
    * 保存修改操作
    * @param object 要保存修改的实体
    * @return 结果
    */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public Map<String, Object> saveOrUpdate(CloudServiceMonitor object) throws Exception {
        Map<String, Object> returnData = new HashMap<String, Object>();
        int num = 0;
        if(object.getServiceId() == null || "".equals(object.getServiceId())) {
            object.setServiceId(UUIDGenerator.INSTANCE.getUUID());
            num = this.insert(object);
            returnData.put("message", "insert");
            returnData.put("number", num);
        } else {
            CloudServiceMonitor object1 = this.cloudServiceMonitorMapper.get(object);
            if(object1 == null) {
                num = this.insert(object);
                returnData.put("message", "insert");
                returnData.put("number", num);
            } else {
                num = this.update(object);
                returnData.put("message", "update");
                returnData.put("number", num);
            }
        }
        return returnData;
    }

    /**
    * 批量插入
    * @param insertObjects 要插入的实体集合
    * @return 插入的记录数
    */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchInsert(List<CloudServiceMonitor> insertObjects) throws Exception {
        List<CloudTimedTask> cloudTimedTaskList = this.batchConvertMonitorToTask(insertObjects);
        this.timedTaskService.batchInsert(cloudTimedTaskList);
        return this.cloudServiceMonitorMapper.batchInsert(insertObjects);
    }

    /**
    * 插入
    * @param object 要插入你的实体
    * @return 插入的记录数
    */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insert(CloudServiceMonitor object) throws Exception {
    List<CloudServiceMonitor> objects = new ArrayList<CloudServiceMonitor>();
        objects.add(object);
        return this.batchInsert(objects);
    }

    /**
    * 更新操作
    * @param object 要更新的实体
    * @return 更新记录数
    */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int update(CloudServiceMonitor object) throws Exception {
    List<CloudServiceMonitor> objects = new ArrayList<CloudServiceMonitor>();
        objects.add(object);
        return this.batchUpdate(objects);
    }

    /**
    * 批量更新
    * @param objects 要更新的实体集合
    * @return 更新的记录数
    */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchUpdate(List<CloudServiceMonitor> objects) throws Exception {
        List<CloudTimedTask> cloudTimedTaskList = this.batchConvertMonitorToTask(objects);
        this.timedTaskService.batchUpdate(cloudTimedTaskList);
        return this.cloudServiceMonitorMapper.batchUpdate(objects);
    }

    /**
    * 查询单个结果
    * @param object 内含要获取实体的ID信息
    * @return 实体信息
    */
    @Override
    public CloudServiceMonitor get(CloudServiceMonitor object) {
        return this.cloudServiceMonitorMapper.get(object);
    }

    @Override
    public CloudServiceMonitor get(String serviceId) {
        CloudServiceMonitor cloudServiceMonitor = new CloudServiceMonitor();
        cloudServiceMonitor.setServiceId(serviceId);
        return this.get(cloudServiceMonitor);
    }

    /**
     * 批量将监控服务转为定时任务
     * @param cloudServiceMonitorList
     * @return
     */
    private List<CloudTimedTask> batchConvertMonitorToTask(List<CloudServiceMonitor> cloudServiceMonitorList) {
        List<CloudTimedTask> cloudTimedTaskList = new ArrayList<CloudTimedTask>(cloudServiceMonitorList.size());
        for (CloudServiceMonitor cloudServiceMonitor : cloudServiceMonitorList) {
            if (StringUtils.isEmpty(cloudServiceMonitor.getCron()) || StringUtils.isEmpty(cloudServiceMonitor.getCron().trim())) {
                // 没一分钟执行一次
                cloudServiceMonitor.setCron("0 0/1 * * * ? ");
            }
            cloudTimedTaskList.add(this.convertMonitorToTask(cloudServiceMonitor));
        }
        return cloudTimedTaskList;
    }
    /**
     * 将监控服务转为定时任务
     * @param cloudServiceMonitor
     * @return
     */
    private CloudTimedTask convertMonitorToTask(CloudServiceMonitor cloudServiceMonitor) {
        CloudTimedTask cloudTimedTask = new CloudTimedTask();
        cloudTimedTask.setTaskId(cloudServiceMonitor.getServiceId());
        cloudTimedTask.setTaskName(cloudServiceMonitor.getServiceName());
        cloudTimedTask.setClazz(monitorClass.get(cloudServiceMonitor.getType()));
        cloudTimedTask.setCron(cloudServiceMonitor.getCron());
        cloudTimedTask.setEnable(cloudServiceMonitor.getEnable());
        cloudTimedTask.setRemark(cloudServiceMonitor.getRemark());
        return cloudTimedTask;
    }
}