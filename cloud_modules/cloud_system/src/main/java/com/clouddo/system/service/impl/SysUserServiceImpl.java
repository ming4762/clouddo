package com.clouddo.system.service.impl;


import com.clouddo.auth.common.model.User;
import com.cloudd.commons.auth.util.UserUtil;
import com.clouddo.commons.common.util.UUIDGenerator;
import com.clouddo.system.mapper.SysUserMapper;
import com.clouddo.system.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

//imports com.charsming.test.service.sysUser.SysUserService;

/**
 * Service层 实现类，用于业务逻辑处理，事务控制等 
 * @author charsmingCodeGenerator 
 */
@Service
public class SysUserServiceImpl implements SysUserService {
	
	@Resource
	private SysUserMapper sysUserMapper;

    /**
     * 查询操作
     * @param parameterSet 参数
     * @return 查询结果
     */
    @Override
    public List<User> list(Map<String, Object> parameterSet) {
        List<User> userList = this.sysUserMapper.list(parameterSet);
        // 隐藏密码
        userList.forEach(user -> user.setPassword(null));
        return userList;
    }

    /**
    * 删除操作
    * @param deleteObjects 要删除的实体对象集合，内含对象主键信息
    */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int delete(List<User> deleteObjects) {
        return this.sysUserMapper.delete(deleteObjects);
    }

    /**
    * 保存修改操作
    * @param object 要保存修改的实体
    * @return 结果
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> saveOrUpdate(User object) {
        Map<String, Object> returnData = new HashMap<String, Object>();
        int num = 0;
        if(object.getUserId() == null || "".equals(object.getUserId())) {
            object.setUserId(UUIDGenerator.getUUID());
            num = this.insert(object);
            returnData.put("message", "insert");
            returnData.put("number", num);
        } else {
            User object1 = this.sysUserMapper.get(object);
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
    public int batchInsert(List<User> insertObjects) {
        Date date = new Date();
        // 设置添加人员/添加时间
        insertObjects.forEach(user -> {
            user.setCreateTime(date);
            user.setCreateUserId(UserUtil.getCurrentUser() != null ? UserUtil.getCurrentUser().getUserId() : null);
            user.setStatus("1");
        });
        return this.sysUserMapper.batchInsert(insertObjects);
    }

    /**
    * 插入
    * @param object 要插入你的实体
    * @return 插入的记录数
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(User object) {
    List<User> objects = new ArrayList<User>();
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
    public int update(User object) {
    List<User> objects = new ArrayList<User>();
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
    public int batchUpdate(List<User> objects) {
        Date date = new Date();
        objects.forEach(user -> {
            user.setUpdateTime(date);
            user.setUpdateUserId(UserUtil.getCurrentUser() != null ? UserUtil.getCurrentUser().getUserId() : null);
        });
        return this.sysUserMapper.batchUpdate(objects);
    }

    /**
    * 查询单个结果
    * @param object 内含要获取实体的ID信息
    * @return 实体信息
    */
    @Override
    public User get(User object) {
        return this.sysUserMapper.get(object);
    }

    /**
     * 查询用户权限
     * @param sysUser
     * @return
     */
    @Override
    public List<String> queryPermissions(User sysUser) {
        return null;
    }
}