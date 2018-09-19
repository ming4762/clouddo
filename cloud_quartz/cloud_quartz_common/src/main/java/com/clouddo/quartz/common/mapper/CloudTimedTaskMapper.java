package com.clouddo.quartz.common.mapper;


import com.clouddo.quartz.common.model.CloudTimedTask;

import java.util.List;
import java.util.Map;


/**
 * Dao层 接口类，用于持久化数据处理
 * @author charsmingCodeGenerator
 */
public interface CloudTimedTaskMapper {

	/**
     * 查询所有
     * @param parameterSet 参数
     * @return 结果
     */
    List<CloudTimedTask> list(Map<String, Object> parameterSet);

    /**
    * 删除操作
    * @param deleteObjects 要删除的实体对象集合，内含对象主键信息
    */
    int delete(List<CloudTimedTask> deleteObjects);


	/**
	* 获取一个对象
	* @param object 实体内含实体主键
	* @return 实体对象
	*/
	CloudTimedTask get(CloudTimedTask object);

	/**
	* 批量插入操作
	* @param insertObjects 要插入的实体集合
	* @return 插入的记录数
	*/
	int batchInsert(List<CloudTimedTask> insertObjects);

	/**
	* 更新操作
	* @param objects 要更新的尸体
	* @return
	*/
	int batchUpdate(List<CloudTimedTask> objects);

}