package com.clouddo.system.mapper;

import com.clouddo.system.model.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 角色与菜单对应关系
 * @author zhongming
 * @since 3.0
 * 2018/5/4下午5:26
 */
@Mapper
public interface RoleMenuMapper {
    RoleMenu get(String id);

    List<RoleMenu> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(RoleMenu roleMenu);

    int update(RoleMenu roleMenu);

    int remove(String id);

    int batchRemove(String[] ids);

    List<String> listMenuIdByRoleId(String roleId);

    int removeByRoleId(String roleId);

    int removeByMenuId(String menuId);

    int batchSave(List<RoleMenu> list);
}
