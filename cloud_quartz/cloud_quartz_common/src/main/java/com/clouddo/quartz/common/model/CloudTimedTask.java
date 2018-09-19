package com.clouddo.quartz.common.model;


import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * <p>实体类</p>
 * <p>Table: cloud_timed_task - 定时任务类</p>
 *
 * @since 2018-08-27 09:43:36
 */
@Table(name="cloud_timed_task")
public class CloudTimedTask implements Serializable  {

    private static final long serialVersionUID = -6735048176105225227L;
    /** TASK_ID - 任务ID */
	@Column(name = "TASK_ID")
    private String taskId;

    /** TASK_NAME - 任务名称 */
    @Column(name = "TASK_NAME")
    private String taskName;

    /** CLASS - 类限定名 */
    @Column(name = "CLASS")
    private String clazz;

    /** CRON - cron表达式 */
    @Column(name = "CRON")
    private String cron;

    /** Enable - 启用 */
    @Column(name = "Enable")
    private Boolean enable;

    /** REMARK - 备注 */
    @Column(name = "REMARK")
    private String remark;


    public String getTaskId(){
        return this.taskId; 
    }
    public void setTaskId(String taskId){
        this.taskId = taskId; 
    }

    public String getTaskName(){
        return this.taskName;
    }
    public void setTaskName(String taskName){
        this.taskName = taskName;
    }

    public String getClazz(){
        return this.clazz;
    }
    public void setClazz(String clazz){
        this.clazz = clazz;
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


    @Override
    public String toString() {
        return "CloudTimedTask{" +
                "taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", clazz='" + clazz + '\'' +
                ", cron='" + cron + '\'' +
                ", enable=" + enable +
                ", remark='" + remark + '\'' +
                '}';
    }
}