package com.clouddo.quartz.common.service;


import com.clouddo.quartz.common.model.CloudTimedTask;
import org.quartz.SchedulerException;

import java.util.List;
import java.util.Map;



/**
 * Service层 接口类，用于业务逻辑处理，事务控制等 
 * @author charsmingCodeGenerator 
 */
public interface CloudTimedTaskService {

    /**
     * 查询所有
     * @param parameterSet 参数
     * @return 结果
     */
    List<CloudTimedTask> list(Map<String, Object> parameterSet);

    /**
    * 删除操作
    * @param deleteObjects 要删除的实体对象集合，内含对象主键信息
    */
    int delete(List<CloudTimedTask> deleteObjects) throws Exception;

    /**
    * 保存修改操作
    * @param object 要保存修改的实体
    * @return 结果
    */
    Map<String,Object> saveOrUpdate(CloudTimedTask object) throws Exception;

    /**
    * 插入操作
    * @param insertObjects 要插入的实体集合
    * @return 插入的记录数
    */
    int batchInsert(List<CloudTimedTask> insertObjects) throws Exception;
    int insert(CloudTimedTask object) throws Exception;

    /**
    * 更新操作
    * @param object 要更新的实体
    * @return 更新记录数
    */
    int update(CloudTimedTask object) throws Exception;

    /**
    * 批量更新
    * @param objects 要更新的实体集合
    * @return 更新的记录数
    */
    int batchUpdate(List<CloudTimedTask> objects) throws Exception;

    /**
    * 查询单个结果
    * @param object 内含要获取实体的ID信息
    * @return 实体信息
    */
    CloudTimedTask get(CloudTimedTask object);

    /**
     * 向定时任务中添加任务
     * @param cloudTimedTask
     */
    void addTask(CloudTimedTask cloudTimedTask) throws ClassNotFoundException, SchedulerException, Exception;

    /**
     * 从定时任务中移除task
     * @param cloudTimedTask
     */
    CloudTimedTask removeTask(CloudTimedTask cloudTimedTask) throws Exception;
    /**
     * 启动 关闭任务
     * @param cloudTimedTask
     * @return
     */
    CloudTimedTask openClose(CloudTimedTask cloudTimedTask) throws Exception;

}