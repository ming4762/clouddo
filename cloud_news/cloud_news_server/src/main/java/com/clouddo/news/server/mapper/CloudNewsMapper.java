package com.clouddo.news.server.mapper;


import com.clouddo.news.server.dto.CloudNewsDTO;
import com.clouddo.news.server.model.CloudNews;

import java.util.List;
import java.util.Map;


/**
 * Dao层 接口类，用于持久化数据处理
 * @author charsmingCodeGenerator
 */
public interface CloudNewsMapper {

	/**
     * 查询所有
     * @param parameterSet 参数
     * @return 结果
     */
    List<CloudNewsDTO> list(Map<String, Object> parameterSet);

    /**
    * 删除操作
    * @param deleteObjects 要删除的实体对象集合，内含对象主键信息
    */
    int delete(List<CloudNews> deleteObjects);


	/**
	* 获取一个对象
	* @param object 实体内含实体主键
	* @return 实体对象
	*/
	CloudNewsDTO get(CloudNews object);

	/**
	* 批量插入操作
	* @param insertObjects 要插入的实体集合
	* @return 插入的记录数
	*/
	int batchInsert(List<CloudNews> insertObjects);

	/**
	* 更新操作
	* @param objects 要更新的尸体
	* @return
	*/
	int batchUpdate(List<CloudNews> objects);

}