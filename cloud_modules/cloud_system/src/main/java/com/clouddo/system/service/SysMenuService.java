package com.clouddo.system.service;


import com.clouddo.commons.common.model.Tree;
import com.clouddo.system.model.SysMenu;

import java.util.List;
import java.util.Map;


/**
 * Service层 接口类，用于业务逻辑处理，事务控制等 
 * @author charsmingCodeGenerator 
 */
public interface SysMenuService {

    /**
     * 查询所有
     * @param parameterSet 参数
     * @return 结果
     */
    List<SysMenu> list(Map<String, Object> parameterSet);

    /**
    * 删除操作
    * @param deleteObjects 要删除的实体对象集合，内含对象主键信息
    */
    int delete(List<SysMenu> deleteObjects);

    /**
    * 保存修改操作
    * @param object 要保存修改的实体
    * @return 结果
    */
    Map<String,Object> saveOrUpdate(SysMenu object);

    /**
    * 插入操作
    * @param insertObjects 要插入的实体集合
    * @return 插入的记录数
    */
    int batchInsert(List<SysMenu> insertObjects);
    int insert(SysMenu object);

    /**
    * 更新操作
    * @param object 要更新的实体
    * @return 更新记录数
    */
    int update(SysMenu object);

    /**
    * 批量更新
    * @param objects 要更新的实体集合
    * @return 更新的记录数
    */
    int batchUpdate(List<SysMenu> objects);

    /**
    * 查询单个结果
    * @param object 内含要获取实体的ID信息
    * @return 实体信息
    */
    SysMenu get(SysMenu object);

    /**
     * 保存顶级菜单
     * @param sysMenu
     * @return
     */
    Map<String,Object> saveUpdateTopMenu(SysMenu sysMenu);

    List<Tree<SysMenu>> listWithAllChildren(Map<String, Object> parameters);

    /**
     * 删除菜单和菜单下级
     * @param deleteObjects
     * @return
     */
    Integer deleteWithChildren(List<SysMenu> deleteObjects);
}