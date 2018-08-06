package com.clouddo.system.service;


import com.clouddo.commons.common.model.Tree;
import com.clouddo.system.model.Dept;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-27 14:28:36
 */
public interface DeptService {

	Dept get(String deptId);
	
	List<Dept> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(Dept sysDept);
	
	int update(Dept sysDept);
	
	int remove(String deptId);
	
	int batchRemove(String[] deptIds);

	Tree<Dept> getTree();
	
	boolean checkDeptHasUser(String deptId);
}
