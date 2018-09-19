package com.clouddo.monitor.server.service;


import com.clouddo.monitor.server.model.CloudMonitorLog;

import java.util.List;
import java.util.Map;



/**
 * Service层 接口类，用于业务逻辑处理，事务控制等 
 * @author charsmingCodeGenerator 
 */
public interface CloudMonitorLogService {

    /**
     * 查询所有
     * @param parameterSet 参数
     * @return 结果
     */
    List<CloudMonitorLog> list(Map<String, Object> parameterSet);

    /**
    * 删除操作
    * @param deleteObjects 要删除的实体对象集合，内含对象主键信息
    */
    int delete(List<CloudMonitorLog> deleteObjects);

    /**
    * 保存修改操作
    * @param object 要保存修改的实体
    * @return 结果
    */
    Map<String,Object> saveOrUpdate(CloudMonitorLog object);

    /**
    * 插入操作
    * @param insertObjects 要插入的实体集合
    * @return 插入的记录数
    */
    int batchInsert(List<CloudMonitorLog> insertObjects);
    int insert(CloudMonitorLog object);

    /**
    * 更新操作
    * @param object 要更新的实体
    * @return 更新记录数
    */
    int update(CloudMonitorLog object);

    /**
    * 批量更新
    * @param objects 要更新的实体集合
    * @return 更新的记录数
    */
    int batchUpdate(List<CloudMonitorLog> objects);

    /**
    * 查询单个结果
    * @param object 内含要获取实体的ID信息
    * @return 实体信息
    */
    CloudMonitorLog get(CloudMonitorLog object);

	
}