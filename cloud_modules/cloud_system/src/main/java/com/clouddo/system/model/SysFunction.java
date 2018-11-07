package com.clouddo.system.model;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


/**
 * <p>实体类</p>
 * <p>Table: sys_function - 系统功能管理</p>
 * @author zhongming
 * @since 2018-10-17 05:26:43
 */
@Table(name="sys_function")
public class SysFunction implements Serializable  {

    private static final long serialVersionUID = -264036880666859091L;
    /** FUNCTION_ID - 功能ID */
	@Column(name = "FUNCTION_ID")
    private String functionId;

    /** PARENT_ID - 上级ID */
    @Column(name = "PARENT_ID")
    private String parentId;

    /** FUNCTION_NAME - 功能名称 */
    @Column(name = "FUNCTION_NAME")
    private String functionName;

    /** FUNCTION_TYPE - 功能类型 */
    @Column(name = "FUNCTION_TYPE")
    private String functionType;

    /** ICON - 图标 */
    @Column(name = "ICON")
    private String icon;

    /** SEQ - 序号 */
    @Column(name = "SEQ")
    private Integer seq;

    /** CREATE_TIME - 创建时间 */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /** CREATE_USER_ID - 创建人员 */
    @Column(name = "CREATE_USER_ID")
    private String createUserId;

    /** MODIFY_TIME - 修改时间 */
    @Column(name = "MODIFY_TIME")
    private Date modifyTime;

    /** MODIFY_USER_ID - 修改人员 */
    @Column(name = "MODIFY_USER_ID")
    private String modifyUserId;

    /** URL - URL */
    @Column(name = "URL")
    private String url;

    /** PREMISSION - 权限 */
    @Column(name = "PREMISSION")
    private String premission;

    /** IS_MENU - 是否菜单 */
    @Column(name = "IS_MENU")
    private Boolean isMenu;


    public String getFunctionId(){
        return this.functionId; 
    }
    public void setFunctionId(String functionId){
        this.functionId = functionId; 
    }

    public String getParentId(){
        return this.parentId;
    }
    public void setParentId(String parentId){
        this.parentId = parentId;
    }

    public String getFunctionName(){
        return this.functionName;
    }
    public void setFunctionName(String functionName){
        this.functionName = functionName;
    }

    public String getFunctionType(){
        return this.functionType;
    }
    public void setFunctionType(String functionType){
        this.functionType = functionType;
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

    public Date getCreateTime(){
        return this.createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    public String getCreateUserId(){
        return this.createUserId;
    }
    public void setCreateUserId(String createUserId){
        this.createUserId = createUserId;
    }

    public Date getModifyTime(){
        return this.modifyTime;
    }
    public void setModifyTime(Date modifyTime){
        this.modifyTime = modifyTime;
    }

    public String getModifyUserId(){
        return this.modifyUserId;
    }
    public void setModifyUserId(String modifyUserId){
        this.modifyUserId = modifyUserId;
    }

    public String getUrl(){
        return this.url;
    }
    public void setUrl(String url){
        this.url = url;
    }

    public String getPremission(){
        return this.premission;
    }
    public void setPremission(String premission){
        this.premission = premission;
    }

    public Boolean getIsMenu(){
        return this.isMenu;
    }
    public void setIsMenu(Boolean isMenu){
        this.isMenu = isMenu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysFunction that = (SysFunction) o;
        return Objects.equals(functionId, that.functionId) &&
                Objects.equals(parentId, that.parentId) &&
                Objects.equals(functionName, that.functionName) &&
                Objects.equals(functionType, that.functionType) &&
                Objects.equals(icon, that.icon) &&
                Objects.equals(seq, that.seq) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(createUserId, that.createUserId) &&
                Objects.equals(modifyTime, that.modifyTime) &&
                Objects.equals(modifyUserId, that.modifyUserId) &&
                Objects.equals(url, that.url) &&
                Objects.equals(premission, that.premission) &&
                Objects.equals(isMenu, that.isMenu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(functionId, parentId, functionName, functionType, icon, seq, createTime, createUserId, modifyTime, modifyUserId, url, premission, isMenu);
    }

    @Override
    public String toString() {
        return "SysFunction{" +
                "functionId='" + functionId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", functionName='" + functionName + '\'' +
                ", functionType='" + functionType + '\'' +
                ", icon='" + icon + '\'' +
                ", seq=" + seq +
                ", createTime=" + createTime +
                ", createUserId='" + createUserId + '\'' +
                ", modifyTime=" + modifyTime +
                ", modifyUserId='" + modifyUserId + '\'' +
                ", url='" + url + '\'' +
                ", premission='" + premission + '\'' +
                ", isMenu=" + isMenu +
                '}';
    }
}