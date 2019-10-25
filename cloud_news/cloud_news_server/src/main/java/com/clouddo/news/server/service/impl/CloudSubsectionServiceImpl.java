package com.clouddo.news.server.service.impl;


import com.clouddo.commons.common.util.UUIDGenerator;
import com.clouddo.news.server.mapper.CloudSubsectionMapper;
import com.clouddo.news.server.model.CloudSubsection;
import com.clouddo.news.server.service.CloudSubsectionService;
import org.springframework.stereotype.Service;

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
public class CloudSubsectionServiceImpl implements CloudSubsectionService {
	
	@Resource
	private CloudSubsectionMapper cloudSubsectionMapper;

    /**
     * 查询操作
     * @param parameterSet 参数
     * @return 查询结果
     */
    @Override
    public List<CloudSubsection> list(Map<String, Object> parameterSet) {
        return this.cloudSubsectionMapper.list(parameterSet);
    }

    /**
    * 删除操作
    * @param deleteObjects 要删除的实体对象集合，内含对象主键信息
    */
    @Override
    public int delete(List<CloudSubsection> deleteObjects) {
        int deleteNum = this.cloudSubsectionMapper.delete(deleteObjects);
        return deleteNum;
    }

    /**
    * 保存修改操作
    * @param object 要保存修改的实体
    * @return 结果
    */
    @Override
    public Map<String, Object> saveOrUpdate(CloudSubsection object) {
        Map<String, Object> returnData = new HashMap<String, Object>();
        int num = 0;
        if(object.getSubsectionId() == null || "".equals(object.getSubsectionId())) {
            object.setSubsectionId(UUIDGenerator.INSTANCE.getUUID());
            num = this.insert(object);
            returnData.put("message", "insert");
            returnData.put("number", num);
        } else {
            CloudSubsection object1 = this.cloudSubsectionMapper.get(object);
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
    public int batchInsert(List<CloudSubsection> insertObjects) {
        return this.cloudSubsectionMapper.batchInsert(insertObjects);
    }

    /**
    * 插入
    * @param object 要插入你的实体
    * @return 插入的记录数
    */
    @Override
    public int insert(CloudSubsection object) {
    List<CloudSubsection> objects = new ArrayList<CloudSubsection>();
        objects.add(object);
        return this.batchInsert(objects);
    }

    /**
    * 更新操作
    * @param object 要更新的实体
    * @return 更新记录数
    */
    @Override
    public int update(CloudSubsection object) {
    List<CloudSubsection> objects = new ArrayList<CloudSubsection>();
        objects.add(object);
        return this.batchUpdate(objects);
    }

    /**
    * 批量更新
    * @param objects 要更新的实体集合
    * @return 更新的记录数
    */
    @Override
    public int batchUpdate(List<CloudSubsection> objects) {
        return this.cloudSubsectionMapper.batchUpdate(objects);
    }

    /**
    * 查询单个结果
    * @param object 内含要获取实体的ID信息
    * @return 实体信息
    */
    @Override
    public CloudSubsection get(CloudSubsection object) {
        return this.cloudSubsectionMapper.get(object);
    }
}