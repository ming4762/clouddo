package com.cloudd.commons.auth.model;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author zhongming
 * @since 3.0
 * 2018/5/4下午4:47
 */
@Table(name = "sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 6669084575509958100L;
    @Column(name = "user_id")
    private String userId;
    // 用户名
    @Column(name = "username")
    private String username;
    // 用户真实姓名
    @Column(name = "name")
    private String name;
    // 密码
    @Column(name = "password")
    private String password;
    // 部门
    @Column(name = "dept_id")
    private String deptId;
//    @Column(name = "user_id")
    private String deptName;
    // 邮箱
    @Column(name = "email")
    private String email;
    // 手机号
    @Column(name = "mobile")
    private String mobile;
    // 状态 0:禁用，1:正常
    @Column(name = "status")
    private Integer status;
    // 创建用户id
    @Column(name = "user_id_create")
    private String userIdCreate;
    // 创建时间
    @Column(name = "gmt_create")
    private Date gmtCreate;
    // 修改时间
    @Column(name = "gmt_modified")
    private Date gmtModified;
    //角色
    private List<String> roleIds;
    //性别
    @Column(name = "sex")
    private Long sex;
    //出身日期
    @Column(name = "birth")
    private Date birth;
    //图片ID
    @Column(name = "pic_id")
    private String picId;
    //现居住地
    @Column(name = "live_address")
    private String liveAddress;
    //爱好
    @Column(name = "hobby")
    private String hobby;
    //省份
    @Column(name = "province")
    private String province;
    //所在城市
    @Column(name = "city")
    private String city;
    //所在地区
    @Column(name = "district")
    private String district;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserIdCreate() {
        return userIdCreate;
    }

    public void setUserIdCreate(String userIdCreate) {
        this.userIdCreate = userIdCreate;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public Long getSex() {
        return sex;
    }

    public void setSex(Long sex) {
        this.sex = sex;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public String getLiveAddress() {
        return liveAddress;
    }

    public void setLiveAddress(String liveAddress) {
        this.liveAddress = liveAddress;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", status=" + status +
                ", userIdCreate=" + userIdCreate +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", roleIds=" + roleIds +
                ", sex=" + sex +
                ", birth=" + birth +
                ", picId=" + picId +
                ", liveAddress='" + liveAddress + '\'' +
                ", hobby='" + hobby + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                '}';
    }
}
