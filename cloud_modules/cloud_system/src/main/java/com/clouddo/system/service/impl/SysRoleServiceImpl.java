package com.clouddo.system.service.impl;


import com.cloudd.commons.auth.util.UserUtil;
import com.clouddo.commons.common.util.UUIDGenerator;
import com.clouddo.system.mapper.SysRoleMapper;
import com.clouddo.system.model.SysRole;
import com.clouddo.system.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

//imports com.charsming.test.service.sysRole.SysRoleService;

/**
 * Service层 实现类，用于业务逻辑处理，事务控制等 
 * @author charsmingCodeGenerator 
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
	
	@Resource
	private SysRoleMapper sysRoleMapper;

    /**
     * 查询操作
     * @param parameterSet 参数
     * @return 查询结果
     */
    @Override
    public List<SysRole> list(Map<String, Object> parameterSet) {
        return this.sysRoleMapper.list(parameterSet);
    }

    /**
    * 删除操作
    * @param deleteObjects 要删除的实体对象集合，内含对象主键信息
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(List<SysRole> deleteObjects) {
        return this.sysRoleMapper.delete(deleteObjects);
    }

    /**
    * 保存修改操作
    * @param object 要保存修改的实体
    * @return 结果
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> saveOrUpdate(SysRole object) {
        Map<String, Object> returnData = new HashMap<String, Object>();
        int num = 0;
        if(object.getRoleId() == null || "".equals(object.getRoleId())) {
            object.setRoleId(UUIDGenerator.INSTANCE.getUUID());
            num = this.insert(object);
            returnData.put("message", "insert");
            returnData.put("number", num);
        } else {
            SysRole object1 = this.sysRoleMapper.get(object);
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
    @Transactional(rollbackFor = Exception.class)
    public int batchInsert(List<SysRole> insertObjects) {
        // 设置添加时间
        insertObjects.forEach(object -> {
            object.setCreateTime(new Date());
            object.setCreateUserId(UserUtil.getCurrentUser() == null ? null : UserUtil.getCurrentUser().getUserId());
        });
        return this.sysRoleMapper.batchInsert(insertObjects);
    }

    /**
    * 插入
    * @param object 要插入你的实体
    * @return 插入的记录数
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(SysRole object) {
        List<SysRole> objects = new ArrayList<SysRole>();
        objects.add(object);
        return this.batchInsert(objects);
    }

    /**
    * 更新操作
    * @param object 要更新的实体
    * @return 更新记录数
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(SysRole object) {
        List<SysRole> objects = new ArrayList<SysRole>();
        objects.add(object);
        return this.batchUpdate(objects);
    }

    /**
    * 批量更新
    * @param objects 要更新的实体集合
    * @return 更新的记录数
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchUpdate(List<SysRole> objects) {
        objects.forEach(object -> {
            object.setUpdateUserId(UserUtil.getCurrentUser() == null ? null : UserUtil.getCurrentUser().getUserId());
            object.setUpdateTime(new Date());
        });
        return this.sysRoleMapper.batchUpdate(objects);
    }

    /**
    * 查询单个结果
    * @param object 内含要获取实体的ID信息
    * @return 实体信息
    */
    @Override
    public SysRole get(SysRole object) {
        return this.sysRoleMapper.get(object);
    }

    /**
     * 查询角色对应人员
     * @param roleList 角色列表
     * @return
     */
    @Override
    public List<SysRole> queryUser(List<SysRole> roleList) {
        return this.sysRoleMapper.queryUser(roleList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setUser(Map<String, List<String>> parameterSet) {
        // 删除角色对应的用户
        Set<String> keySet = new HashSet<String>();
        parameterSet.forEach((key, value) -> {
            keySet.add(key);
        });
        Integer deleteNum = this.sysRoleMapper.deleteRoleUser(keySet);
        // 添加角色用户
        List<Map<String, String>> parameters = new ArrayList<Map<String, String>>();
        parameterSet.forEach((key, value) -> {
            value.forEach(userId -> {
                Map<String, String> map = new HashMap<String, String>();
                map.put("roleId", key);
                map.put("userId", userId);
                parameters.add(map);
            });
        });
        if (parameters.size() > 0) {
            Integer insertNum = this.sysRoleMapper.saveRoleUser(parameters);
        }
    }
}