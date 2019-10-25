package com.clouddo.monitor.server.service.impl;


import com.clouddo.commons.common.util.UUIDGenerator;
import com.clouddo.monitor.server.mapper.CloudMonitorLogMapper;
import com.clouddo.monitor.server.model.CloudMonitorLog;
import com.clouddo.monitor.server.service.CloudMonitorLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


/**
 * Service层 实现类，用于业务逻辑处理，事务控制等 
 * @author charsmingCodeGenerator 
 */
@Service
public class CloudMonitorLogServiceImpl implements CloudMonitorLogService {
	
	@Resource
	private CloudMonitorLogMapper cloudMonitorLogMapper;

    /**
     * 查询操作
     * @param parameterSet 参数
     * @return 查询结果
     */
    @Override
    public List<CloudMonitorLog> list(Map<String, Object> parameterSet) {
        final String startKey = "startTime";
        final String endKey = "endTime";
        if (parameterSet.get(startKey) == null || parameterSet.get(endKey) == null) {
            Calendar calendar = Calendar.getInstance();
            parameterSet.put(endKey, calendar.getTime());
            // 获取24小时内的数据
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 24);
            parameterSet.put(startKey, calendar.getTime());
        }
        return this.cloudMonitorLogMapper.list(parameterSet);
    }

    /**
    * 删除操作
    * @param deleteObjects 要删除的实体对象集合，内含对象主键信息
    */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int delete(List<CloudMonitorLog> deleteObjects) {
        return this.cloudMonitorLogMapper.delete(deleteObjects);
    }

    /**
    * 保存修改操作
    * @param object 要保存修改的实体
    * @return 结果
    */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public Map<String, Object> saveOrUpdate(CloudMonitorLog object) {
        Map<String, Object> returnData = new HashMap<String, Object>();
        int num = 0;
        if(object.getLogId() == null || "".equals(object.getLogId())) {
            object.setLogId(UUIDGenerator.INSTANCE.getUUID());
            num = this.insert(object);
            returnData.put("message", "insert");
            returnData.put("number", num);
        } else {
            CloudMonitorLog object1 = this.cloudMonitorLogMapper.get(object);
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
    public int batchInsert(List<CloudMonitorLog> insertObjects) {
        return this.cloudMonitorLogMapper.batchInsert(insertObjects);
    }

    /**
    * 插入
    * @param object 要插入你的实体
    * @return 插入的记录数
    */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insert(CloudMonitorLog object) {
    List<CloudMonitorLog> objects = new ArrayList<CloudMonitorLog>();
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
    public int update(CloudMonitorLog object) {
    List<CloudMonitorLog> objects = new ArrayList<CloudMonitorLog>();
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
    public int batchUpdate(List<CloudMonitorLog> objects) {
        return this.cloudMonitorLogMapper.batchUpdate(objects);
    }

    /**
    * 查询单个结果
    * @param object 内含要获取实体的ID信息
    * @return 实体信息
    */
    @Override
    public CloudMonitorLog get(CloudMonitorLog object) {
        return this.cloudMonitorLogMapper.get(object);
    }
}