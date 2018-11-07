package com.clouddo.system.service.impl;


import com.cloudd.commons.auth.util.UserUtil;
import com.clouddo.commons.common.util.UUIDGenerator;
import com.clouddo.system.mapper.SysFunctionMapper;
import com.clouddo.system.model.SysFunction;
import com.clouddo.system.service.SysFunctionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * Service层 实现类，用于业务逻辑处理，事务控制等 
 * @author charsmingCodeGenerator 
 */
@Service
public class SysFunctionServiceImpl implements SysFunctionService {
	
	@Resource
	private SysFunctionMapper sysFunctionMapper;

    /**
     * 查询操作
     * @param parameterSet 参数
     * @return 查询结果
     */
    @Override
    public List<SysFunction> list(Map<String, Object> parameterSet) {
        return this.sysFunctionMapper.list(parameterSet);
    }

    /**
    * 删除操作
    * @param deleteObjects 要删除的实体对象集合，内含对象主键信息
    */
    @Override
    public int delete(List<SysFunction> deleteObjects) {
        return this.sysFunctionMapper.delete(deleteObjects);
    }

    /**
    * 保存修改操作
    * @param object 要保存修改的实体
    * @return 结果
    */
    @Override
    public Map<String, Object> saveOrUpdate(SysFunction object) {
        Map<String, Object> returnData = new HashMap<String, Object>();
        int num = 0;
        if(object.getFunctionId() == null || "".equals(object.getFunctionId())) {
            object.setFunctionId(UUIDGenerator.getUUID());
            num = this.insert(object);
            returnData.put("message", "insert");
            returnData.put("number", num);
        } else {
            SysFunction object1 = this.sysFunctionMapper.get(object);
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
    public int batchInsert(List<SysFunction> insertObjects) {
        return this.sysFunctionMapper.batchInsert(insertObjects);
    }

    /**
    * 插入
    * @param object 要插入你的实体
    * @return 插入的记录数
    */
    @Override
    public int insert(SysFunction object) {
        List<SysFunction> objects = new ArrayList<SysFunction>();
        // 设置添加时间
        object.setCreateTime(new Date());
        object.setCreateUserId(UserUtil.getCurrentUser() == null ? null : UserUtil.getCurrentUser().getUserId());
        objects.add(object);
        return this.batchInsert(objects);
    }

    /**
    * 更新操作
    * @param object 要更新的实体
    * @return 更新记录数
    */
    @Override
    public int update(SysFunction object) {
        List<SysFunction> objects = new ArrayList<SysFunction>();
        object.setModifyTime(new Date());
        object.setModifyUserId(UserUtil.getCurrentUser() == null ? null : UserUtil.getCurrentUser().getUserId());
        objects.add(object);
        return this.batchUpdate(objects);
    }

    /**
    * 批量更新
    * @param objects 要更新的实体集合
    * @return 更新的记录数
    */
    @Override
    public int batchUpdate(List<SysFunction> objects) {
        return this.sysFunctionMapper.batchUpdate(objects);
    }

    /**
    * 查询单个结果
    * @param object 内含要获取实体的ID信息
    * @return 实体信息
    */
    @Override
    public SysFunction get(SysFunction object) {
        return this.sysFunctionMapper.get(object);
    }
}