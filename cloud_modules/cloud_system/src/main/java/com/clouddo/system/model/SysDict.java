package com.clouddo.system.model;


import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * <p>实体类</p>
 * <p>Table: sys_dict - 字典</p>
 *
 * @since 2018-08-22 03:36:32
 */
@Table(name="sys_dict")
public class SysDict implements Serializable  {

    private static final long serialVersionUID = -435979643050862897L;
    /** DICT_CODE - 字典ID */
	@Column(name = "DICT_CODE")
    private String dictCode;

    /** DICT_NAME - 字典名称 */
    @Column(name = "DICT_NAME")
    private String dictName;

    /** SET_MAN - 最近修改人 */
    @Column(name = "SET_MAN")
    private String setMan;

    /** SET_TIME - 最近修改时间 */
    @Column(name = "SET_TIME")
    private String setTime;

    /** NOTE - 描述 */
    @Column(name = "NOTE")
    private String note;

    /** IN_USE - 是否启用 */
    @Column(name = "IN_USE")
    private String inUse;


    public String getDictCode(){
        return this.dictCode; 
    }
    public void setDictCode(String dictCode){
        this.dictCode = dictCode; 
    }

    public String getDictName(){
        return this.dictName;
    }
    public void setDictName(String dictName){
        this.dictName = dictName;
    }

    public String getSetMan(){
        return this.setMan;
    }
    public void setSetMan(String setMan){
        this.setMan = setMan;
    }

    public String getSetTime(){
        return this.setTime;
    }
    public void setSetTime(String setTime){
        this.setTime = setTime;
    }

    public String getNote(){
        return this.note;
    }
    public void setNote(String note){
        this.note = note;
    }

    public String getInUse(){
        return this.inUse;
    }
    public void setInUse(String inUse){
        this.inUse = inUse;
    }
}