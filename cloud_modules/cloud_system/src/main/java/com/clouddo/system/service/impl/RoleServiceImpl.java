package com.clouddo.system.service.impl;

import com.clouddo.commons.common.util.UUIDGenerator;
import com.clouddo.system.dto.MenuDTO;
import com.clouddo.system.mapper.RoleMapper;
import com.clouddo.system.mapper.RoleMenuMapper;
import com.clouddo.system.mapper.UserMapper;
import com.clouddo.system.mapper.UserRoleMapper;
import com.clouddo.system.model.Role;
import com.clouddo.system.model.RoleMenu;
import com.clouddo.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class RoleServiceImpl implements RoleService {

    public static final String ROLE_ALL_KEY = "\"role_all\"";

    public static final String DEMO_CACHE_NAME = "role";

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    RoleMenuMapper roleMenuMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    public List<Role> list() {
        List<Role> roles = roleMapper.list(new HashMap<>(16));
        return roles;
    }


    @Override
    public List<Role> list(String userId) {
        List<String> rolesIds = userRoleMapper.listRoleId(userId);
        List<Role> roles = roleMapper.list(new HashMap<>(16));
        for (com.clouddo.system.model.Role Role : roles) {
            Role.setRoleSign("false");
            for (String roleId : rolesIds) {
                if (Objects.equals(Role.getRoleId(), roleId)) {
                    Role.setRoleSign("true");
                    break;
                }
            }
        }
        return roles;
    }
    @Transactional
    @Override
    public int save(Role role) {
        int count = roleMapper.save(role);
        List<String> menuIds = role.getMenuIds();
        String roleId = role.getRoleId();
        List<RoleMenu> rms = new ArrayList<>();
        for (String menuId : menuIds) {
            RoleMenu rmDo = new RoleMenu();
            rmDo.setRoleId(roleId);
            rmDo.setMenuId(menuId);
            rms.add(rmDo);
        }
        roleMenuMapper.removeByRoleId(roleId);
        if (rms.size() > 0) {
            roleMenuMapper.batchSave(rms);
        }
        return count;
    }

    @Transactional
    @Override
    public int remove(String id) {
        int count = roleMapper.remove(id);
        userRoleMapper.removeByRoleId(id);
        roleMenuMapper.removeByRoleId(id);
        return count;
    }

    @Override
    public Role get(String id) {
        Role Role = roleMapper.get(id);
        return Role;
    }

    @Override
    public int update(Role role) {
        int r = roleMapper.update(role);
        List<String> menuIds = role.getMenuIds();
        String roleId = role.getRoleId();
        roleMenuMapper.removeByRoleId(roleId);
        List<RoleMenu> rms = new ArrayList<>();
        for (String menuId : menuIds) {
            RoleMenu rmDo = new RoleMenu();
            rmDo.setId(UUIDGenerator.INSTANCE.getUUID());
            rmDo.setRoleId(roleId);
            rmDo.setMenuId(menuId);
            rms.add(rmDo);
        }
        if (rms.size() > 0) {
            roleMenuMapper.batchSave(rms);
        }
        return r;
    }

    @Override
    public int batchremove(String[] ids) {
        int r = roleMapper.batchRemove(ids);
        return r;
    }

    /**
     * 查询角色拥有的菜单ID
     * @param roleId
     * @return
     */
    @Override
    public List<String> listMenuIdByRole(String roleId) {
        List<MenuDTO> menuDTOList = this.listMenuByRole(roleId);
        List<String> menuIdList = new ArrayList<>(menuDTOList.size());
        for (MenuDTO menuDTO : menuDTOList) {
            menuIdList.add(menuDTO.getMenuId());
        }
        return menuIdList;
    }

    /**
     * 查询角色拥有的菜单信息
     * @param roleId
     * @return
     */
    @Override
    public List<MenuDTO> listMenuByRole(String roleId) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("roleId", roleId);
        return this.roleMapper.listMenuByRole(parameters);
    }

}
