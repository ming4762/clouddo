package com.clouddo.system.service.impl;

import com.clouddo.commons.common.model.Tree;
import com.clouddo.commons.common.util.BuildTree;
import com.clouddo.system.mapper.DeptMapper;
import com.clouddo.system.model.Dept;
import com.clouddo.system.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DeptServiceImpl implements DeptService {
	@Autowired
	private DeptMapper sysDeptMapper;

	@Override
	public Dept get(String deptId){
		return sysDeptMapper.get(deptId);
	}

	@Override
	public List<Dept> list(Map<String, Object> map){
		return sysDeptMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map){
		return sysDeptMapper.count(map);
	}

	@Override
	public int save(Dept sysDept){
		return sysDeptMapper.save(sysDept);
	}

	@Override
	public int update(Dept sysDept){
		return sysDeptMapper.update(sysDept);
	}

	@Override
	public int remove(String deptId){
		return sysDeptMapper.remove(deptId);
	}

	@Override
	public int batchRemove(String[] deptIds){
		return sysDeptMapper.batchRemove(deptIds);
	}

	@Override
	public Tree<Dept> getTree() {
		List<Tree<Dept>> trees = new ArrayList<Tree<Dept>>();
		List<Dept> sysDepts = sysDeptMapper.list(new HashMap<String,Object>(16));
		for (Dept sysDept : sysDepts) {
			Tree<Dept> tree = new Tree<Dept>();
			tree.setId(sysDept.getDeptId().toString());
			tree.setParentId(sysDept.getParentId().toString());
			tree.setText(sysDept.getName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<Dept> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public boolean checkDeptHasUser(String deptId) {
		// TODO Auto-generated method stub
		//查询部门以及此部门的下级部门
		int result = sysDeptMapper.getDeptUserNumber(deptId);
		return result==0?true:false;
	}

}
