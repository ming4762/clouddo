package com.clouddo.system.service;


import com.clouddo.auth.common.model.User;

import java.util.List;
import java.util.Map;


/**
 * Service层 接口类，用于业务逻辑处理，事务控制等 
 * @author charsmingCodeGenerator 
 */
public interface SysUserService {

    /**
     * 查询所有
     * @param parameterSet 参数
     * @return 结果
     */
    List<User> list(Map<String, Object> parameterSet);

    /**
    * 删除操作
    * @param deleteObjects 要删除的实体对象集合，内含对象主键信息
    */
    int delete(List<User> deleteObjects);

    /**
    * 保存修改操作
    * @param object 要保存修改的实体
    * @return 结果
    */
    Map<String,Object> saveOrUpdate(User object);

    /**
    * 插入操作
    * @param insertObjects 要插入的实体集合
    * @return 插入的记录数
    */
    int batchInsert(List<User> insertObjects);
    int insert(User object);

    /**
    * 更新操作
    * @param object 要更新的实体
    * @return 更新记录数
    */
    int update(User object);

    /**
    * 批量更新
    * @param objects 要更新的实体集合
    * @return 更新的记录数
    */
    int batchUpdate(List<User> objects);

    /**
    * 查询单个结果
    * @param object 内含要获取实体的ID信息
    * @return 实体信息
    */
    User get(User object);

    /**
     * 查询用户权限
     * @param sysUser
     * @return
     */
    List<String> queryPermissions(User sysUser);
}