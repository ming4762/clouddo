package com.clouddo.system.mapper;

import com.clouddo.system.dto.MenuDTO;
import com.clouddo.system.model.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 菜单mapper
 * @author zhongming
 * @since 3.0
 * 2018/5/4下午5:20
 */
@Mapper
public interface MenuMapper {
    Menu get(String menuId);

    List<MenuDTO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(Menu menu);

    int update(Menu menu);

    int remove(String menuId);

    int batchRemove(String[] menuIds);

    List<Menu> listMenuByUserId(String id);

    List<String> listUserPerms(String id);

    /**
     * 批量删除接口
     * @param ids
     * @return 删除记录数
     */
    Integer batchDelete(@Param("set") Set<String> ids);

    /**
     * 查询下级菜单ID
     * @param ids
     * @return
     */
    Set<String> listChildId(@Param("set") Set<String> ids);
}
