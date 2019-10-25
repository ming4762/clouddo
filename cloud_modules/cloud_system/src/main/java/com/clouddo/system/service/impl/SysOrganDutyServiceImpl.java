package com.clouddo.system.service.impl;


import com.clouddo.commons.common.util.UUIDGenerator;
import com.clouddo.system.mapper.SysOrganDutyMapper;
import com.clouddo.system.model.SysOrganDuty;
import com.clouddo.system.service.SysOrganDutyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//imports com.charsming.test.service.sysOrganDuty.SysOrganDutyService;

/**
 * Service层 实现类，用于业务逻辑处理，事务控制等 
 * @author charsmingCodeGenerator 
 */
@Service
public class SysOrganDutyServiceImpl implements SysOrganDutyService {
	
	@Resource
	private SysOrganDutyMapper sysOrganDutyMapper;

    /**
     * 查询操作
     * @param parameterSet 参数
     * @return 查询结果
     */
    @Override
    public List<SysOrganDuty> list(Map<String, Object> parameterSet) {
        return this.sysOrganDutyMapper.list(parameterSet);
    }

    /**
    * 删除操作
    * @param deleteObjects 要删除的实体对象集合，内含对象主键信息
    */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int delete(List<SysOrganDuty> deleteObjects) {
        return this.sysOrganDutyMapper.delete(deleteObjects);
    }

    /**
    * 保存修改操作
    * @param object 要保存修改的实体
    * @return 结果
    */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public Map<String, Object> saveOrUpdate(SysOrganDuty object) {
        Map<String, Object> returnData = new HashMap<String, Object>();
        int num = 0;
        if(object.getOrganId() == null || "".equals(object.getOrganId())) {
            object.setOrganId(UUIDGenerator.INSTANCE.getUUID());
            num = this.insert(object);
            returnData.put("message", "insert");
            returnData.put("number", num);
        } else {
            SysOrganDuty object1 = this.sysOrganDutyMapper.get(object);
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
    public int batchInsert(List<SysOrganDuty> insertObjects) {
        return this.sysOrganDutyMapper.batchInsert(insertObjects);
    }

    /**
    * 插入
    * @param object 要插入你的实体
    * @return 插入的记录数
    */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insert(SysOrganDuty object) {
    List<SysOrganDuty> objects = new ArrayList<SysOrganDuty>();
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
    public int update(SysOrganDuty object) {
    List<SysOrganDuty> objects = new ArrayList<SysOrganDuty>();
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
    public int batchUpdate(List<SysOrganDuty> objects) {
        return this.sysOrganDutyMapper.batchUpdate(objects);
    }

    /**
    * 查询单个结果
    * @param object 内含要获取实体的ID信息
    * @return 实体信息
    */
    @Override
    public SysOrganDuty get(SysOrganDuty object) {
        return this.sysOrganDutyMapper.get(object);
    }
}