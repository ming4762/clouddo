package com.clouddo.system.model;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * <p>实体类</p>
 * <p>Table: sys_organ_duty - 组织机构/职务</p>
 * @author zhongming
 * @since 2018-09-06 01:20:38
 */
@Table(name="sys_organ_duty")
public class SysOrganDuty implements Serializable  {

    private static final long serialVersionUID = 4612680088397815209L;
    /** ORGAN_ID - ID */
	@Column(name = "ORGAN_ID")
    private String organId;

    /** PARENT_ID - 上级ID */
    @Column(name = "PARENT_ID")
    private String parentId;

    /** ORGAN_NAME - 名称 */
    @Column(name = "ORGAN_NAME")
    private String organName;

    /** SHORT_NAME - 短名称 */
    @Column(name = "SHORT_NAME")
    private String shortName;

    /** ORGAN_TYPE - 类型 */
    @Column(name = "ORGAN_TYPE")
    private String organType;

    /** ORGAN_LEVEL - 级别 */
    @Column(name = "ORGAN_LEVEL")
    private Integer organLevel;

    /** ADDVCD - 行政区编码 */
    @Column(name = "ADDVCD")
    private String addvcd;

    /** CREATE_TIME - 创建时间 */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /** CREATE_USER_ID - 创建人员ID */
    @Column(name = "CREATE_USER_ID")
    private String createUserId;

    /** UPDATE_TIME - 更新时间 */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /** UPDATE_USER_ID - 更新人员ID */
    @Column(name = "UPDATE_USER_ID")
    private String updateUserId;

    /** SEQ - 序号 */
    @Column(name = "SEQ")
    private Integer seq;

    /** IN_USE - 是否启用 */
    @Column(name = "IN_USE")
    private String inUse;

    /** ICON - 图标 */
    @Column(name = "ICON")
    private String icon;

    /** IDENT - 标识 */
    @Column(name = "IDENT")
    private String ident;

    /** DUTY - 职务 */
    @Column(name = "DUTY")
    private String duty;

    /** REMARK - 备注 */
    @Column(name = "REMARK")
    private String remark;


    public String getOrganId(){
        return this.organId; 
    }
    public void setOrganId(String organId){
        this.organId = organId; 
    }

    public String getParentId(){
        return this.parentId;
    }
    public void setParentId(String parentId){
        this.parentId = parentId;
    }

    public String getOrganName(){
        return this.organName;
    }
    public void setOrganName(String organName){
        this.organName = organName;
    }

    public String getShortName(){
        return this.shortName;
    }
    public void setShortName(String shortName){
        this.shortName = shortName;
    }

    public String getOrganType(){
        return this.organType;
    }
    public void setOrganType(String organType){
        this.organType = organType;
    }

    public Integer getOrganLevel(){
        return this.organLevel;
    }
    public void setOrganLevel(Integer organLevel){
        this.organLevel = organLevel;
    }

    public String getAddvcd(){
        return this.addvcd;
    }
    public void setAddvcd(String addvcd){
        this.addvcd = addvcd;
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

    public Date getUpdateTime(){
        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }

    public String getUpdateUserId(){
        return this.updateUserId;
    }
    public void setUpdateUserId(String updateUserId){
        this.updateUserId = updateUserId;
    }

    public Integer getSeq(){
        return this.seq;
    }
    public void setSeq(Integer seq){
        this.seq = seq;
    }

    public String getInUse(){
        return this.inUse;
    }
    public void setInUse(String inUse){
        this.inUse = inUse;
    }

    public String getIcon(){
        return this.icon;
    }
    public void setIcon(String icon){
        this.icon = icon;
    }

    public String getIdent(){
        return this.ident;
    }
    public void setIdent(String ident){
        this.ident = ident;
    }

    public String getDuty(){
        return this.duty;
    }
    public void setDuty(String duty){
        this.duty = duty;
    }

    public String getRemark(){
        return this.remark;
    }
    public void setRemark(String remark){
        this.remark = remark;
    }
}