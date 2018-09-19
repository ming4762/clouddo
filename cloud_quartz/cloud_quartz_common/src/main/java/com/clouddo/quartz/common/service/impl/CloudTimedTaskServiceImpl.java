package com.clouddo.quartz.common.service.impl;


import com.clouddo.commons.common.util.UUIDGenerator;
import com.clouddo.quartz.common.mapper.CloudTimedTaskMapper;
import com.clouddo.quartz.common.model.CloudTimedTask;
import com.clouddo.quartz.common.service.CloudTimedTaskService;
import com.clouddo.quartz.common.service.QuartzService;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class CloudTimedTaskServiceImpl implements CloudTimedTaskService {
	
	@Resource
	private CloudTimedTaskMapper cloudTimedTaskMapper;

    @Autowired
    private QuartzService quartzService;

    /**
     * 查询操作
     * @param parameterSet 参数
     * @return 查询结果
     */
    @Override
    public List<CloudTimedTask> list(Map<String, Object> parameterSet) {
        return this.cloudTimedTaskMapper.list(parameterSet);
    }

    /**
    * 删除操作
    * @param deleteObjects 要删除的实体对象集合，内含对象主键信息
    */
    @Override
    @Transactional(value = "quartzTransactionManager", rollbackFor = Exception.class)
    public int delete(List<CloudTimedTask> deleteObjects) throws Exception {
        int number = this.cloudTimedTaskMapper.delete(deleteObjects);
        for (CloudTimedTask cloudTimedTask : deleteObjects) {
            this.quartzService.removeTask(cloudTimedTask);
        }
        return number;
    }

    /**
    * 保存修改操作
    * @param object 要保存修改的实体
    * @return 结果
    */
    @Override
    @Transactional(value = "quartzTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> saveOrUpdate(CloudTimedTask object) throws Exception {
        Map<String, Object> returnData = new HashMap<String, Object>();
        int num = 0;
        if(object.getTaskId() == null || "".equals(object.getTaskId())) {
            object.setTaskId(UUIDGenerator.getUUID());
            num = this.insert(object);
            returnData.put("message", "insert");
            returnData.put("number", num);
        } else {
            CloudTimedTask object1 = this.cloudTimedTaskMapper.get(object);
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
    @Transactional(value = "quartzTransactionManager", rollbackFor = Exception.class)
    public int batchInsert(List<CloudTimedTask> insertObjects) throws Exception {
        int number =  this.cloudTimedTaskMapper.batchInsert(insertObjects);
        for (CloudTimedTask cloudTimedTask : insertObjects) {
            if (cloudTimedTask.getEnable()) {
                this.quartzService.addTask(cloudTimedTask);
            }
        }
        return number;
    }

    /**
    * 插入
    * @param object 要插入你的实体
    * @return 插入的记录数
    */
    @Override
    @Transactional(value = "quartzTransactionManager", rollbackFor = Exception.class)
    public int insert(CloudTimedTask object) throws Exception {
        List<CloudTimedTask> objects = new ArrayList<CloudTimedTask>();
        objects.add(object);
        return this.batchInsert(objects);
    }

    /**
    * 更新操作
    * @param object 要更新的实体
    * @return 更新记录数
    */
    @Override
    @Transactional(value = "quartzTransactionManager", rollbackFor = Exception.class)
    public int update(CloudTimedTask object) throws Exception {
        List<CloudTimedTask> objects = new ArrayList<CloudTimedTask>();
        objects.add(object);
        return this.batchUpdate(objects);
    }

    /**
    * 批量更新
    * @param objects 要更新的实体集合
    * @return 更新的记录数
    */
    @Override
    @Transactional(value = "quartzTransactionManager", rollbackFor = Exception.class)
    public int batchUpdate(List<CloudTimedTask> objects) throws Exception {
        int number = this.cloudTimedTaskMapper.batchUpdate(objects);
        for (CloudTimedTask cloudTimedTask : objects) {
            this.quartzService.removeTask(cloudTimedTask);
            if (cloudTimedTask.getEnable()) {
                this.quartzService.addTask(cloudTimedTask);
            }
        }
        return number;
    }

    /**
    * 查询单个结果
    * @param object 内含要获取实体的ID信息
    * @return 实体信息
    */
    @Override
    public CloudTimedTask get(CloudTimedTask object) {
        return this.cloudTimedTaskMapper.get(object);
    }

    /**
     * 向定时任务中添加任务
     * @param cloudTimedTask
     */
    @Override
    public void addTask(CloudTimedTask cloudTimedTask) throws Exception {
        //判断类是否继承自 QuartzJobBean
        quartzService.addTask(cloudTimedTask);

    }

    /**
     * 从定时任务中移除task
     * @param cloudTimedTask
     */
    @Override
    public CloudTimedTask removeTask(CloudTimedTask cloudTimedTask) throws Exception {
        //查询定时任务
        cloudTimedTask = this.cloudTimedTaskMapper.get(cloudTimedTask);
        this.quartzService.removeTask(cloudTimedTask);
        return cloudTimedTask;
    }


    /**
     * 启动 关闭任务
     * @param cloudTimedTask
     * @return
     */
    @Override
    public CloudTimedTask openClose(CloudTimedTask cloudTimedTask) throws Exception {
        boolean enable = cloudTimedTask.getEnable();
        //查询定时任务
        cloudTimedTask = this.cloudTimedTaskMapper.get(cloudTimedTask);
        if (cloudTimedTask != null) {
            cloudTimedTask.setEnable(enable);
            List<CloudTimedTask> cloudTimedTaskList = new ArrayList<CloudTimedTask>();
            cloudTimedTaskList.add(cloudTimedTask);
            this.cloudTimedTaskMapper.batchUpdate(cloudTimedTaskList);
            //启动或关闭定时任务
            if (enable) {
                //判断任务是否存在
                JobDetail jobDetail = this.quartzService.getJob(cloudTimedTask);
                if (jobDetail == null) {
                    this.quartzService.addTask(cloudTimedTask);
                } else {
                    this.quartzService.resumeTask(cloudTimedTask);
                }
            } else {
                this.quartzService.pauseTask(cloudTimedTask);
            }
        }
        return null;
    }


}