package com.clouddo.system.model;

import java.io.Serializable;

/**
 * @author zhongming
 * @since 3.0
 * 2018/5/4下午5:27
 */
public class RoleMenu implements Serializable {
    private static final long serialVersionUID = -6656361459804066869L;
    private String id;
    private String  roleId;
    private String menuId;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getMenuId() {
        return menuId;
    }
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return "RoleMenu{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", menuId=" + menuId +
                '}';
    }



}
