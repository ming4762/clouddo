package com.clouddo.system.mapper;

import com.clouddo.system.model.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author zhongming
 * @since 3.0
 * 2018/5/15下午5:08
 */
@Mapper
public interface RoleMapper {

    Role get(String roleId);

    List<Role> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(Role role);

    int update(Role role);

    int remove(String roleId);

    int batchRemove(String[] roleIds);
}
