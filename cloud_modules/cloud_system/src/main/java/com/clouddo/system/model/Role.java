package com.clouddo.system.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class Role implements Serializable {

	private static final long serialVersionUID = -977157267181758411L;
	private String roleId;
	private String roleName;
	private String roleSign;
	private String remark;
	private String userIdCreate;
	private Timestamp gmtCreate;
	private Timestamp gmtModified;
	private List<String> menuIds;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleSign() {
		return roleSign;
	}

	public void setRoleSign(String roleSign) {
		this.roleSign = roleSign;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserIdCreate() {
		return userIdCreate;
	}

	public void setUserIdCreate(String userIdCreate) {
		this.userIdCreate = userIdCreate;
	}

	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Timestamp getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Timestamp gmtModified) {
		this.gmtModified = gmtModified;
	}

	public List<String> getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(List<String> menuIds) {
		this.menuIds = menuIds;
	}

	@Override
	public String toString() {
		return "Role{" +
				"roleId=" + roleId +
				", roleName='" + roleName + '\'' +
				", roleSign='" + roleSign + '\'' +
				", remark='" + remark + '\'' +
				", userIdCreate=" + userIdCreate +
				", gmtCreate=" + gmtCreate +
				", gmtModified=" + gmtModified +
				", menuIds=" + menuIds +
				'}';
	}
}
