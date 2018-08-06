package com.clouddo.news.server.model;


import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * <p>实体类</p>
 * <p>Table: cloud_subsection - 子栏目</p>
 *
 * @since 2018-07-31 11:02:27
 */
@Table(name="cloud_subsection")
public class CloudSubsection implements Serializable  {

    private static final long serialVersionUID = -2454609027602890795L;
    /** SUBSECTION_ID - 子栏目ID */
	@Column(name = "SUBSECTION_ID")
    private String subsectionId;

    /** SUBSECTION_NAME - 子栏目名称 */
    @Column(name = "SUBSECTION_NAME")
    private String subsectionName;

    /** REMARK - 备注 */
    @Column(name = "REMARK")
    private String remark;

    /** COLUMN_ID - 栏目ID */
    @Column(name = "COLUMN_ID")
    private String columnId;


    public String getSubsectionId(){
        return this.subsectionId; 
    }
    public void setSubsectionId(String subsectionId){
        this.subsectionId = subsectionId; 
    }

    public String getSubsectionName(){
        return this.subsectionName;
    }
    public void setSubsectionName(String subsectionName){
        this.subsectionName = subsectionName;
    }

    public String getRemark(){
        return this.remark;
    }
    public void setRemark(String remark){
        this.remark = remark;
    }

    public String getColumnId(){
        return this.columnId;
    }
    public void setColumnId(String columnId){
        this.columnId = columnId;
    }
}