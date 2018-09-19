package com.clouddo.monitor.server.model;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * <p>实体类</p>
 * <p>Table: cloud_monitor_log - 服务监控日志</p>
 *
 * @since 2018-08-30 10:36:56
 */
@Table(name="cloud_monitor_log")
public class CloudMonitorLog implements Serializable  {

    private static final long serialVersionUID = 9201521469272850438L;
    /** log_id - 记录ID */
	@Column(name = "log_id")
    private String logId;

    /** service_id - 服务ID */
    @Column(name = "service_id")
    private String serviceId;

    @Column(name = "status")
    private Boolean status;
    /** code - code */
    @Column(name = "code")
    private String code;

    /** use_time - 用时 */
    @Column(name = "use_time")
    private Integer useTime;

    /** time - 时间 */
    @Column(name = "time")
    private Date time;

    /** message - 信息 */
    @Column(name = "message")
    private String message;


    public String getLogId(){
        return this.logId; 
    }
    public void setLogId(String logId){
        this.logId = logId; 
    }

    public String getServiceId(){
        return this.serviceId;
    }
    public void setServiceId(String serviceId){
        this.serviceId = serviceId;
    }

    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }

    public Integer getUseTime(){
        return this.useTime;
    }
    public void setUseTime(Integer useTime){
        this.useTime = useTime;
    }

    public Date getTime(){
        return this.time;
    }
    public void setTime(Date time){
        this.time = time;
    }

    public String getMessage(){
        return this.message;
    }
    public void setMessage(String message){
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CloudMonitorLog{" +
                "logId='" + logId + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", status=" + status +
                ", code='" + code + '\'' +
                ", useTime=" + useTime +
                ", time=" + time +
                ", message='" + message + '\'' +
                '}';
    }
}