package com.clouddo.system.service;

import com.clouddo.commons.common.model.Tree;
import com.clouddo.system.dto.MenuDTO;
import com.clouddo.system.model.Menu;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public interface MenuService {
	Tree<Menu> getSysMenuTree(String id);

	List<Tree<Menu>> listMenuTree(String id);

	Tree<Menu> getTree();

	Tree<Menu> getTree(String id);

	List<MenuDTO> list(Map<String, Object> params);

	int remove(String id);

	int save(Menu menu);

	int update(Menu menu);

	Menu get(String id);

	Set<String> listPerms(String userId);

	/**
	 * 批量删除
	 * @param menuList
	 * @return
	 */
    Integer batchDelete(List<Menu> menuList);

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	Integer batchDelete(Set<String> ids);
}
