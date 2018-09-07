package com.clouddo.system.service;

import com.clouddo.system.dto.MenuDTO;
import com.clouddo.system.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {

	Role get(String id);

	List<Role> list();

	int save(Role role);

	int update(Role role);

	int remove(String id);

	List<Role> list(String userId);

	int batchremove(String[] ids);

	/**
	 * 查询角色拥有的菜单ID
	 * @param roleId
	 * @return
	 */
	List<String> listMenuIdByRole(String roleId);

	/**
	 * 查询角色拥有的菜单信息
	 * @param roleId
	 * @return
	 */
	List<MenuDTO> listMenuByRole(String roleId);
}
