package com.clouddo.system.model;

import com.cloudd.commons.auth.model.User;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * <p>实体类</p>
 * <p>Table: sys_role - 系统角色表</p>
 *
 * @since 2018-11-08 08:32:04
 */
@Table(name="sys_role")
public class SysRole implements Serializable  {

    private static final long serialVersionUID = -3256589831587452170L;

    private List<User> userList;

    /** role_id - 角色ID */
	@Column(name = "role_id")
    private String roleId;

    /** role_name - 角色名 */
    @Column(name = "role_name")
    private String roleName;

    /** role_sign -  */
    @Column(name = "role_sign")
    private String roleSign;

    /** remark - 备注 */
    @Column(name = "remark")
    private String remark;

    /** create_user_id - 创建人员ID */
    @Column(name = "create_user_id")
    private String createUserId;

    /** create_time - 创建时间 */
    @Column(name = "create_time")
    private Date createTime;

    /** update_user_id - 修改人员ID */
    @Column(name = "update_user_id")
    private String updateUserId;

    /** update_time - 修改时间 */
    @Column(name = "update_time")
    private Date updateTime;

    /** dept_id - 所属机构ID */
    @Column(name = "dept_id")
    private String deptId;

    /** global - 全局 */
    @Column(name = "global")
    private Boolean global;

    @Column(name = "seq")
    private Integer seq;


    public String getRoleId(){
        return this.roleId; 
    }
    public void setRoleId(String roleId){
        this.roleId = roleId; 
    }

    public String getRoleName(){
        return this.roleName;
    }
    public void setRoleName(String roleName){
        this.roleName = roleName;
    }

    public String getRoleSign(){
        return this.roleSign;
    }
    public void setRoleSign(String roleSign){
        this.roleSign = roleSign;
    }

    public String getRemark(){
        return this.remark;
    }
    public void setRemark(String remark){
        this.remark = remark;
    }

    public String getCreateUserId(){
        return this.createUserId;
    }
    public void setCreateUserId(String createUserId){
        this.createUserId = createUserId;
    }

    public Date getCreateTime(){
        return this.createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    public String getUpdateUserId(){
        return this.updateUserId;
    }
    public void setUpdateUserId(String updateUserId){
        this.updateUserId = updateUserId;
    }

    public Date getUpdateTime(){
        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }

    public String getDeptId(){
        return this.deptId;
    }
    public void setDeptId(String deptId){
        this.deptId = deptId;
    }

    public Boolean getGlobal(){
        return this.global;
    }
    public void setGlobal(Boolean global){
        this.global = global;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        SysRole sysRole = (SysRole) o;
        return Objects.equals(roleId, sysRole.roleId) &&
                Objects.equals(roleName, sysRole.roleName) &&
                Objects.equals(roleSign, sysRole.roleSign) &&
                Objects.equals(remark, sysRole.remark) &&
                Objects.equals(createUserId, sysRole.createUserId) &&
                Objects.equals(createTime, sysRole.createTime) &&
                Objects.equals(updateUserId, sysRole.updateUserId) &&
                Objects.equals(updateTime, sysRole.updateTime) &&
                Objects.equals(deptId, sysRole.deptId) &&
                Objects.equals(global, sysRole.global);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, roleName, roleSign, remark, createUserId, createTime, updateUserId, updateTime, deptId, global);
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}