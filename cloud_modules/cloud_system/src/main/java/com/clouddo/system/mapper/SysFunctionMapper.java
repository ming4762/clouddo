package com.clouddo.system.mapper;


import com.clouddo.system.model.SysFunction;

import java.util.List;
import java.util.Map;


/**
 * Dao层 接口类，用于持久化数据处理
 * @author charsmingCodeGenerator
 */
public interface SysFunctionMapper {

	/**
     * 查询所有
     * @param parameterSet 参数
     * @return 结果
     */
    List<SysFunction> list(Map<String, Object> parameterSet);

    /**
    * 删除操作
    * @param deleteObjects 要删除的实体对象集合，内含对象主键信息
    */
    int delete(List<SysFunction> deleteObjects);


	/**
	* 获取一个对象
	* @param sysFunction 实体内含实体主键
	* @return 实体对象
	*/
	SysFunction get(SysFunction sysFunction);

	/**
	* 批量插入操作
	* @param sysFunctions 要插入的实体集合
	* @return 插入的记录数
	*/
	int batchInsert(List<SysFunction> sysFunctions);

	/**
	* 更新操作
	* @param sysFunctions 要更新的尸体
	* @return
	*/
	int batchUpdate(List<SysFunction> sysFunctions);

}