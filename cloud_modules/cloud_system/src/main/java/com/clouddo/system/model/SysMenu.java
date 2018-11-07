package com.clouddo.system.model;


import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;


/**
 * <p>实体类</p>
 * <p>Table: sys_menu - 系统菜单表</p>
 * @author zhongming
 * @since 2018-11-02 03:17:56
 */
@Table(name="sys_menu")
public class SysMenu implements Serializable  {

    private static final long serialVersionUID = 2077003576466260943L;

    private List<SysMenu> children;

    /** MENU_ID - 菜单ID */
	@Column(name = "MENU_ID")
    private String menuId;

    /** MENU_NAME - 菜单名称 */
    @Column(name = "MENU_NAME")
    private String menuName;

    /** PARENT_ID - 上级ID */
    @Column(name = "PARENT_ID")
    private String parentId;

    /** MENU_TYPE - 菜单类型 */
    @Column(name = "MENU_TYPE")
    private String menuType;

    /** FUNCTION_ID - 功能ID */
    @Column(name = "FUNCTION_ID")
    private String functionId;

    /** ICON - 图标 */
    @Column(name = "ICON")
    private String icon;

    /** SEQ - 序号 */
    @Column(name = "SEQ")
    private Integer seq;


    public String getMenuId(){
        return this.menuId; 
    }
    public void setMenuId(String menuId){
        this.menuId = menuId; 
    }

    public String getMenuName(){
        return this.menuName;
    }
    public void setMenuName(String menuName){
        this.menuName = menuName;
    }

    public String getParentId(){
        return this.parentId;
    }
    public void setParentId(String parentId){
        this.parentId = parentId;
    }

    public String getMenuType(){
        return this.menuType;
    }
    public void setMenuType(String menuType){
        this.menuType = menuType;
    }

    public String getFunctionId(){
        return this.functionId;
    }
    public void setFunctionId(String functionId){
        this.functionId = functionId;
    }

    public String getIcon(){
        return this.icon;
    }
    public void setIcon(String icon){
        this.icon = icon;
    }

    public Integer getSeq(){
        return this.seq;
    }
    public void setSeq(Integer seq){
        this.seq = seq;
    }

    public List<SysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenu> children) {
        this.children = children;
    }
}