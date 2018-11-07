package com.clouddo.system.service.impl;


import com.clouddo.commons.common.model.Tree;
import com.clouddo.commons.common.util.BuildTree;
import com.clouddo.commons.common.util.UUIDGenerator;
import com.clouddo.system.mapper.SysMenuMapper;
import com.clouddo.system.model.SysMenu;
import com.clouddo.system.service.SysMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service层 实现类，用于业务逻辑处理，事务控制等 
 * @author charsmingCodeGenerator 
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
	
	@Resource
	private SysMenuMapper sysMenuMapper;

    /**
     * 查询操作
     * @param parameterSet 参数
     * @return 查询结果
     */
    @Override
    public List<SysMenu> list(Map<String, Object> parameterSet) {
        return this.sysMenuMapper.list(parameterSet);
    }

    /**
    * 删除操作
    * @param deleteObjects 要删除的实体对象集合，内含对象主键信息
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(List<SysMenu> deleteObjects) {
        return this.sysMenuMapper.delete(deleteObjects);
    }

    /**
    * 保存修改操作
    * @param object 要保存修改的实体
    * @return 结果
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> saveOrUpdate(SysMenu object) {
        Map<String, Object> returnData = new HashMap<String, Object>();
        int num = 0;
        if(object.getMenuId() == null || "".equals(object.getMenuId())) {
            object.setMenuId(UUIDGenerator.getUUID());
            num = this.insert(object);
            returnData.put("message", "insert");
            returnData.put("number", num);
        } else {
            SysMenu object1 = this.sysMenuMapper.get(object);
            if(object1 == null) {
                num = this.insert(object);
                returnData.put("message", "insert");
                returnData.put("number", num);
            } else {
                num = this.update(object);
                returnData.put("message", "update");
                returnData.put("number", num);
            }
        }
        return returnData;
    }

    /**
    * 批量插入
    * @param insertObjects 要插入的实体集合
    * @return 插入的记录数
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchInsert(List<SysMenu> insertObjects) {
        return this.sysMenuMapper.batchInsert(insertObjects);
    }

    /**
    * 插入
    * @param object 要插入你的实体
    * @return 插入的记录数
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(SysMenu object) {
        List<SysMenu> objects = new ArrayList<SysMenu>();
        objects.add(object);
        return this.batchInsert(objects);
    }

    /**
    * 更新操作
    * @param object 要更新的实体
    * @return 更新记录数
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(SysMenu object) {
        List<SysMenu> objects = new ArrayList<SysMenu>();
        objects.add(object);
        return this.batchUpdate(objects);
    }

    /**
    * 批量更新
    * @param objects 要更新的实体集合
    * @return 更新的记录数
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchUpdate(List<SysMenu> objects) {
        return this.sysMenuMapper.batchUpdate(objects);
    }

    /**
    * 查询单个结果
    * @param object 内含要获取实体的ID信息
    * @return 实体信息
    */
    @Override
    public SysMenu get(SysMenu object) {
        return this.sysMenuMapper.get(object);
    }

    /**
     * 保存顶级菜单
     * @param sysMenu
     * @return
     */
    @Override
    public Map<String, Object> saveUpdateTopMenu(SysMenu sysMenu) {
        // 获取序号
        if (StringUtils.isEmpty(sysMenu.getMenuId())) {
            sysMenu.setMenuId(UUIDGenerator.getUUID());
        }
        sysMenu.setParentId("0");
        sysMenu.setMenuType("0");
        return this.saveOrUpdate(sysMenu);
    }

    /**
     * 查询数据和全部节点
     * @param parameters
     * @return
     */
    @Override
    public List<Tree<SysMenu>> listWithAllChildren(Map<String, Object> parameters) {
        List<SysMenu> sysMenuList = this.list(new HashMap<>());
        // 可以使用递归查询数据库实现
        if (sysMenuList != null) {
            // 获取自身
            List<String> menuIdList = (List<String>) parameters.get("menuIdList");
            if (menuIdList != null) {
                List<SysMenu> topMenuList =  sysMenuList.stream().filter(
                        menu -> menuIdList.contains(menu.getMenuId())
                ).collect(Collectors.toList());

                List<SysMenu> result = new ArrayList<SysMenu>();
                // 获取所有子类
                this.getAllChild(topMenuList, sysMenuList, result);
                // 将菜单转为树形实体
                List<Tree<SysMenu>> treeList = this.menuToTree(result);
                List<Tree<SysMenu>> topTreeList = this.menuToTree(topMenuList);
                // 构建树形结构
                topTreeList.forEach(
                        tree -> tree.setChildren(BuildTree.buildList(treeList, tree.getId()))
                );
                return topTreeList;
            }
        }
        return null;
    }

    /**
     * 删除菜单和菜单下级
     * @param deleteObjects
     * @return
     */
    @Override
    public Integer deleteWithChildren(List<SysMenu> deleteObjects) {
        // 构建树形
        List<Tree<SysMenu>> treeList = this.menuToTree(deleteObjects);
        this.getAllChildren(treeList);
        // 获取要删除的所有ID
        List<String> idList = new ArrayList<String>();
        this.getIdsFromTree(treeList, idList);

        // 执行删除
        return this.delete(idList.stream().map(id -> {
            SysMenu sysMenu = new SysMenu();
            sysMenu.setMenuId(id);
            return sysMenu;
        }).collect(Collectors.toList()));
    }

    /**
     * 从树形结构中获取所有ID
     * @param treeList
     * @param idList
     */
    private void getIdsFromTree(List<Tree<SysMenu>> treeList, List<String> idList) {
        treeList.forEach(tree -> {
            idList.add(tree.getId());
            if (tree.getChildren() != null && tree.getChildren().size() > 0) {
                this.getIdsFromTree(tree.getChildren(), idList);
            }
        });
    }

    /**
     * 获取所有下级使用递归实现
     * @param treeList
     */
    private void getAllChildren (List<Tree<SysMenu>> treeList) {
        treeList.forEach(tree -> {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("parentId", tree.getId());
            List<SysMenu> children = this.list(parameters);
            if (children != null && children.size() > 0) {
                List<Tree<SysMenu>> trees = this.menuToTree(children);
                tree.setChildren(trees);
                tree.setChildren(true);
                this.getAllChildren(trees);
            }
        });
    }

    /**
     * 将菜单列表转为Tree列表
     * @param sysMenuList
     * @return
     */
    private List<Tree<SysMenu>> menuToTree(List<SysMenu> sysMenuList) {
        return sysMenuList.stream().map(menu -> {
            Tree<SysMenu> tree = new Tree<SysMenu>();
            tree.setId(menu.getMenuId());
            tree.setText(menu.getMenuName());
            tree.setObject(menu);
            tree.setParentId(menu.getParentId());
            return tree;
        }).collect(Collectors.toList());
    }

    private void getAllChild (List<SysMenu> menuList, List<SysMenu> allMenuList, List<SysMenu> result) {
        List<String> menuIdList = menuList.stream()
                .map(SysMenu :: getMenuId)
                .collect(Collectors.toList());
        // 删除已经选择的元素
        List<SysMenu> newMenuList = allMenuList.stream().filter(
                menu -> !menuIdList.contains(menu.getMenuId())
        ).collect(Collectors.toList());
        // 过滤获取下级元素
        List<SysMenu> childMenu = newMenuList.stream().filter(
                menu -> menuIdList.contains(menu.getParentId())
        ).collect(Collectors.toList());
        if (childMenu.size() > 0) {
            result.addAll(childMenu);
            // 执行递归
            this.getAllChild(childMenu, newMenuList, result);
        }
    }
}