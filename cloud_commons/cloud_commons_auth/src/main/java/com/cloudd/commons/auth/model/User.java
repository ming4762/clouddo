package com.cloudd.commons.auth.model;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * <p>实体类</p>
 * <p>Table: sys_user - 系统用户表</p>
 *
 * @since 2018-11-07 03:16:40
 */
@Table(name="sys_user")
public class User implements Serializable  {

    private static final long serialVersionUID = 3575235880000100645L;
    /** user_id - 用户ID */
    @Column(name = "user_id")
    private String userId;

    /** username - 用户名 */
    @Column(name = "username")
    private String username;

    /** name - 姓名 */
    @Column(name = "name")
    private String name;

    /** password - 密码 */
    @Column(name = "password")
    private String password;

    /** email - email */
    @Column(name = "email")
    private String email;

    /** mobile - 手机 */
    @Column(name = "mobile")
    private Integer mobile;

    /** status - 状态 */
    @Column(name = "status")
    private String status;

    /** create_user_id - 创建人ID */
    @Column(name = "create_user_id")
    private String createUserId;

    /** create_time - 创建时间 */
    @Column(name = "create_time")
    private Date createTime;

    /** update_user_id - 更新人员ID */
    @Column(name = "update_user_id")
    private String updateUserId;

    /** update_time - 更新时间 */
    @Column(name = "update_time")
    private Date updateTime;

    /** sex - 性别 */
    @Column(name = "sex")
    private Integer sex;

    /** pic_id - 头像ID */
    @Column(name = "pic_id")
    private String picId;


    public String getUserId(){
        return this.userId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUsername(){
        return this.username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public Integer getMobile(){
        return this.mobile;
    }
    public void setMobile(Integer mobile){
        this.mobile = mobile;
    }

    public String getStatus(){
        return this.status;
    }
    public void setStatus(String status){
        this.status = status;
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

    public Integer getSex(){
        return this.sex;
    }
    public void setSex(Integer sex){
        this.sex = sex;
    }

    public String getPicId(){
        return this.picId;
    }
    public void setPicId(String picId){
        this.picId = picId;
    }
}