package com.clouddo.news.server.model;


import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * <p>实体类</p>
 * <p>Table: cloud_column - 栏目信息</p>
 *
 * @since 2018-07-30 04:45:53
 */
@Table(name="cloud_column")
public class CloudColumn implements Serializable  {

    private static final long serialVersionUID = 4630847316891180366L;
    /** COLUMN_ID - 栏目ID */
	@Column(name = "COLUMN_ID")
    private String columnId;

    /** COLUMN_NAME - 栏目名称 */
    @Column(name = "COLUMN_NAME")
    private String columnName;

    /** REMARK - 备注 */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 所在模块ID
     */
    private String moduleId;


    public String getColumnId(){
        return this.columnId; 
    }
    public void setColumnId(String columnId){
        this.columnId = columnId; 
    }

    public String getColumnName(){
        return this.columnName;
    }
    public void setColumnName(String columnName){
        this.columnName = columnName;
    }

    public String getRemark(){
        return this.remark;
    }
    public void setRemark(String remark){
        this.remark = remark;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }
}