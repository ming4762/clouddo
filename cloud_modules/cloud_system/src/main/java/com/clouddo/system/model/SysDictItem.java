package com.clouddo.system.model;


import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * <p>实体类</p>
 * <p>Table: sys_dict_item - 字典项</p>
 *
 * @since 2018-08-22 03:36:47
 */
@Table(name="sys_dict_item")
public class SysDictItem implements Serializable  {

    private static final long serialVersionUID = 8815617486955896267L;
    /** ITEM_CODE - 编号 */
	@Column(name = "ITEM_CODE")
    private String itemCode;

    /** DICT_CODE - 字典编码 */
	@Column(name = "DICT_CODE")
    private String dictCode;

    /** ITEM_VALUE - 名称 */
    @Column(name = "ITEM_VALUE")
    private String itemValue;

    /** SEQ - 序号 */
    @Column(name = "SEQ")
    private Integer seq;

    /** PARENT_CODE - 上级编码 */
    @Column(name = "PARENT_CODE")
    private String parentCode;

    /** NOTE - 描述 */
    @Column(name = "NOTE")
    private String note;

    /** IN_USE - 是否启用 */
    @Column(name = "IN_USE")
    private String inUse;


    public String getItemCode(){
        return this.itemCode; 
    }
    public void setItemCode(String itemCode){
        this.itemCode = itemCode; 
    }

    public String getDictCode(){
        return this.dictCode; 
    }
    public void setDictCode(String dictCode){
        this.dictCode = dictCode; 
    }

    public String getItemValue(){
        return this.itemValue;
    }
    public void setItemValue(String itemValue){
        this.itemValue = itemValue;
    }

    public Integer getSeq(){
        return this.seq;
    }
    public void setSeq(Integer seq){
        this.seq = seq;
    }

    public String getParentCode(){
        return this.parentCode;
    }
    public void setParentCode(String parentCode){
        this.parentCode = parentCode;
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