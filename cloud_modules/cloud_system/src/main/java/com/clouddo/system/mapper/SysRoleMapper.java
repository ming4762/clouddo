package com.clouddo.system.mapper;


import com.clouddo.system.model.SysRole;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Dao层 接口类，用于持久化数据处理
 * @author charsmingCodeGenerator
 */
public interface SysRoleMapper {

	/**
     * 查询所有
     * @param parameterSet 参数
     * @return 结果
     */
    List<SysRole> list(Map<String, Object> parameterSet);

    /**
    * 删除操作
    * @param deleteObjects 要删除的实体对象集合，内含对象主键信息
    */
    int delete(List<SysRole> deleteObjects);


	/**
	* 获取一个对象
	* @param sysRole 实体内含实体主键
	* @return 实体对象
	*/
	SysRole get(SysRole sysRole);

	/**
	* 批量插入操作
	* @param sysRoles 要插入的实体集合
	* @return 插入的记录数
	*/
	int batchInsert(List<SysRole> sysRoles);

	/**
	* 更新操作
	* @param sysRoles 要更新的尸体
	* @return
	*/
	int batchUpdate(List<SysRole> sysRoles);

	/**
	 * 查询角色对应人员
	 * @param roleList 角色列表
	 * @return
	 */
	List<SysRole> queryUser(List<SysRole> roleList);

	/**
	 * 删除角色用户
	 * @param roleIdList
	 * @return
	 */
	Integer deleteRoleUser(Set<String> roleIdList);

	/**
	 * 保存角色用户
	 * @param parameters
	 * @return
	 */
	Integer saveRoleUser(List<Map<String, String>> parameters);
}