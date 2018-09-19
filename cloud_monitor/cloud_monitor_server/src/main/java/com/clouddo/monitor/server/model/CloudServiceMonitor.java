package com.clouddo.monitor.server.model;


import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * <p>实体类</p>
 * <p>Table: cloud_service_monitor - 服务监控</p>
 * @author zhongming
 * @since 2018-08-28 08:53:56
 */
@Table(name="cloud_service_monitor")
public class CloudServiceMonitor implements Serializable  {

    private static final long serialVersionUID = 5434717378758507765L;
    /** SERVICE_ID - 服务ID */
	@Column(name = "SERVICE_ID")
    private String serviceId;

    /** SERVICE_NAME - 服务名称 */
    @Column(name = "SERVICE_NAME")
    private String serviceName;

    /** TYPE - 类型 */
    @Column(name = "TYPE")
    private String type;

    /** ADDRESS - 地址 */
    @Column(name = "ADDRESS")
    private String address;

    /** CRON - 定时规则 */
    @Column(name = "CRON")
    private String cron;

    /** ENABLE - 启用 */
    @Column(name = "ENABLE")
    private Boolean enable;

    /** REMARK - 备注 */
    @Column(name = "REMARK")
    private String remark;
    @Column(name = "STATUS")
    private String status;


    public String getServiceId(){
        return this.serviceId; 
    }
    public void setServiceId(String serviceId){
        this.serviceId = serviceId; 
    }

    public String getServiceName(){
        return this.serviceName;
    }
    public void setServiceName(String serviceName){
        this.serviceName = serviceName;
    }

    public String getType(){
        return this.type;
    }
    public void setType(String type){
        this.type = type;
    }

    public String getAddress(){
        return this.address;
    }
    public void setAddress(String address){
        this.address = address;
    }

    public String getCron(){
        return this.cron;
    }
    public void setCron(String cron){
        this.cron = cron;
    }

    public Boolean getEnable(){
        return this.enable;
    }
    public void setEnable(Boolean enable){
        this.enable = enable;
    }

    public String getRemark(){
        return this.remark;
    }
    public void setRemark(String remark){
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}