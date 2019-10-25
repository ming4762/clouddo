package com.clouddo.ai.server.service.impl;


import com.clouddo.ai.server.mapper.CloudIntentMapper;
import com.clouddo.ai.server.model.CloudIntentDO;
import com.clouddo.ai.server.model.CloudSlotDO;
import com.clouddo.ai.server.service.CloudIntentService;
import com.clouddo.commons.common.util.UUIDGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
@Transactional
public class CloudIntentServiceImpl implements CloudIntentService {
	
	@Resource
	private CloudIntentMapper cloudIntentMapper;

    /**
     * 查询操作
     * @param parameterSet 参数
     * @return 查询结果
     */
    @Override
    public List<CloudIntentDO> list(Map<String, Object> parameterSet) {
        return this.cloudIntentMapper.list(parameterSet);
    }

    /**
    * 删除操作
    * @param deleteObjects 要删除的实体对象集合，内含对象主键信息
    */
    @Override
    public int delete(List<CloudIntentDO> deleteObjects) {
        int deleteNum = this.cloudIntentMapper.delete(deleteObjects);
        return deleteNum;
    }

    /**
    * 保存修改操作
    * @param object 要保存修改的实体
    * @return 结果
    */
    @Override
    public Map<String, Object> saveOrUpdate(CloudIntentDO object) {
        Map<String, Object> returnData = new HashMap<String, Object>();
        int num = 0;
        if(object.getIntentId() == null || "".equals(object.getIntentId())) {
            object.setIntentId(UUIDGenerator.INSTANCE.getUUID());
            num = this.insert(object);
            returnData.put("message", "insert");
            returnData.put("number", num);
        } else {
            CloudIntentDO object1 = this.cloudIntentMapper.get(object);
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
    public int batchInsert(List<CloudIntentDO> insertObjects) {
        return this.cloudIntentMapper.batchInsert(insertObjects);
    }

    /**
    * 插入
    * @param object 要插入你的实体
    * @return 插入的记录数
    */
    @Override
    public int insert(CloudIntentDO object) {
    List<CloudIntentDO> objects = new ArrayList<CloudIntentDO>();
        objects.add(object);
        return this.batchInsert(objects);
    }

    /**
    * 更新操作
    * @param object 要更新的实体
    * @return 更新记录数
    */
    @Override
    public int update(CloudIntentDO object) {
    List<CloudIntentDO> objects = new ArrayList<CloudIntentDO>();
        objects.add(object);
        return this.batchUpdate(objects);
    }

    /**
    * 批量更新
    * @param objects 要更新的实体集合
    * @return 更新的记录数
    */
    @Override
    public int batchUpdate(List<CloudIntentDO> objects) {
        return this.cloudIntentMapper.batchUpdate(objects);
    }

    /**
    * 查询单个结果
    * @param object 内含要获取实体的ID信息
    * @return 实体信息
    */
    @Override
    public CloudIntentDO get(CloudIntentDO object) {
        return this.cloudIntentMapper.get(object);
    }


    /**
     * 查询意图信息和意图对应的词条信息
     * @param cloudIntent 意图信息，内含意图ID
     * @return 意图信息
     */
    @Override
    public CloudIntentDO getWithSolts(CloudIntentDO cloudIntent) {
        cloudIntent = this.cloudIntentMapper.getWithSolts(cloudIntent);
        if(cloudIntent != null) {
            //将词槽信息放入map中
            List<CloudSlotDO> cloudSlotList = cloudIntent.getCloudSlotList();
            if (cloudSlotList == null || cloudSlotList.size() == 0 || StringUtils.isEmpty(cloudSlotList.get(0).getSlotId())) {
                cloudIntent.setCloudSlotList(null);
            } else {
                for(CloudSlotDO cloudSlot : cloudSlotList) {
                    cloudIntent.getCloudSlotMap().put(cloudSlot.getSlotName(), cloudSlot);
                }
            }
        }
        return cloudIntent;
    }
}