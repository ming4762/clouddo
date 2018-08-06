package com.clouddo.system.service.impl;

import com.clouddo.commons.common.model.Tree;
import com.clouddo.commons.common.util.BuildTree;
import com.clouddo.system.mapper.MenuMapper;
import com.clouddo.system.mapper.RoleMenuMapper;
import com.clouddo.system.model.Menu;
import com.clouddo.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@SuppressWarnings("AlibabaRemoveCommentedCode")
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {
	@Autowired
    MenuMapper menuMapper;
	@Autowired
    RoleMenuMapper roleMenuMapper;

	/**
	 * @param
	 * @return 树形菜单
	 */
	@Cacheable
	@Override
	public Tree<Menu> getSysMenuTree(String id) {
		List<Tree<Menu>> trees = new ArrayList<Tree<Menu>>();
		List<Menu> Menus = menuMapper.listMenuByUserId(id);
		for (Menu sysMenu : Menus) {
			Tree<Menu> tree = new Tree<Menu>();
			tree.setId(sysMenu.getMenuId().toString());
			tree.setParentId(sysMenu.getParentId().toString());
			tree.setText(sysMenu.getName());
			Map<String, Object> attributes = new HashMap<>(16);
			attributes.put("url", sysMenu.getUrl());
			attributes.put("icon", sysMenu.getIcon());
			tree.setAttributes(attributes);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<Menu> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public List<Menu> list(Map<String, Object> params) {
		List<Menu> menus = menuMapper.list(params);
		return menus;
	}

	@Transactional(readOnly = false,rollbackFor = Exception.class)
	@Override
	public int remove(String id) {
		int result = menuMapper.remove(id);
		return result;
	}
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	@Override
	public int save(Menu menu) {
		int r = menuMapper.save(menu);
		return r;
	}

	@Transactional(readOnly = false,rollbackFor = Exception.class)
	@Override
	public int update(Menu menu) {
		int r = menuMapper.update(menu);
		return r;
	}

	@Override
	public Menu get(String id) {
		Menu Menu = menuMapper.get(id);
		return Menu;
	}

	@Override
	public Tree<Menu> getTree() {
		List<Tree<Menu>> trees = new ArrayList<Tree<Menu>>();
		List<Menu> Menus = menuMapper.list(new HashMap<>(16));
		for (Menu sysMenu : Menus) {
			Tree<Menu> tree = new Tree<Menu>();
			tree.setId(sysMenu.getMenuId().toString());
			tree.setParentId(sysMenu.getParentId().toString());
			tree.setText(sysMenu.getName());
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<Menu> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public Tree<Menu> getTree(String id) {
		// 根据roleId查询权限
		List<Menu> menus = menuMapper.list(new HashMap<String, Object>(16));
		List<String> menuIds = roleMenuMapper.listMenuIdByRoleId(id);
		List<String> temp = menuIds;
		for (Menu menu : menus) {
			if (temp.contains(menu.getParentId())) {
				menuIds.remove(menu.getParentId());
			}
		}
		List<Tree<Menu>> trees = new ArrayList<Tree<Menu>>();
		List<Menu> Menus = menuMapper.list(new HashMap<String, Object>(16));
		for (Menu sysMenu : Menus) {
			Tree<Menu> tree = new Tree<Menu>();
			tree.setId(sysMenu.getMenuId().toString());
			tree.setParentId(sysMenu.getParentId().toString());
			tree.setText(sysMenu.getName());
			Map<String, Object> state = new HashMap<>(16);
			String menuId = sysMenu.getMenuId();
			if (menuIds.contains(menuId)) {
				state.put("selected", true);
			} else {
				state.put("selected", false);
			}
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<Menu> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public Set<String> listPerms(String userId) {
		List<String> perms = menuMapper.listUserPerms(userId);
		Set<String> permsSet = new HashSet<>();
		for (String perm : perms) {
			if (!StringUtils.isEmpty(perm)) {
				permsSet.addAll(Arrays.asList(perm.trim().split(",")));
			}
		}
		return permsSet;
	}

	@Override
	public List<Tree<Menu>> listMenuTree(String id) {
		List<Tree<Menu>> trees = new ArrayList<Tree<Menu>>();
		List<Menu> Menus = menuMapper.listMenuByUserId(id);
		for (Menu sysMenu : Menus) {
			Tree<Menu> tree = new Tree<Menu>();
			tree.setId(sysMenu.getMenuId().toString());
			tree.setParentId(sysMenu.getParentId().toString());
			tree.setText(sysMenu.getName());
			Map<String, Object> attributes = new HashMap<>(16);
			attributes.put("url", sysMenu.getUrl());
			attributes.put("icon", sysMenu.getIcon());
			tree.setAttributes(attributes);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		List<Tree<Menu>> list = BuildTree.buildList(trees, "0");
		return list;
	}

}
