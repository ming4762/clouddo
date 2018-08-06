package com.clouddo.system.mapper;

import com.clouddo.system.model.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 菜单mapper
 * @author zhongming
 * @since 3.0
 * 2018/5/4下午5:20
 */
@Mapper
public interface MenuMapper {
    Menu get(String menuId);

    List<Menu> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(Menu menu);

    int update(Menu menu);

    int remove(String menuId);

    int batchRemove(String[] menuIds);

    List<Menu> listMenuByUserId(String id);

    List<String> listUserPerms(String id);
}
