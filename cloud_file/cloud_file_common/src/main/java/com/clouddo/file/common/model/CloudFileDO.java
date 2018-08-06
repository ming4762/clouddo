package com.clouddo.file.common.model;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * <p>实体类</p>
 * <p>Table: cloud_file - 文件实体类</p>
 * @author zhongming
 * @since 2.0
 *
 * 018-07-24 10:56:44
 */
@Table(name="cloud_file")
public class CloudFileDO implements Serializable  {

    private static final long serialVersionUID = -3045164187099247481L;
    /** FILE_ID - 文件ID */
	@Column(name = "FILE_ID")
    private String fileId;

    /** FILE_NAME - 文件名 */
    @Column(name = "FILE_NAME")
    private String fileName;

    /** CREATE_TIME - 创建时间 */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /** TYPE - 类型 */
    @Column(name = "TYPE")
    private String type;

    /** CONTENT_TYPE - 文件类型 */
    @Column(name = "CONTENT_TYPE")
    private String contentType;

    /** SIZE - 文件大小 */
    @Column(name = "SIZE")
    private Long size;

    /** DB_ID - 存储在文件服务器的ID */
    @Column(name = "DB_ID")
    private String dbId;

    /** MD5 - 文件的MD5 */
    @Column(name = "MD5")
    private String md5;

    /** SEQ - 序号 */
    @Column(name = "SEQ")
    private Integer seq;





    public String getFileId(){
        return this.fileId; 
    }
    public void setFileId(String fileId){
        this.fileId = fileId; 
    }

    public String getFileName(){
        return this.fileName;
    }
    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public Date getCreateTime(){
        return this.createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    public String getType(){
        return this.type;
    }
    public void setType(String type){
        this.type = type;
    }

    public String getContentType(){
        return this.contentType;
    }
    public void setContentType(String contentType){
        this.contentType = contentType;
    }

    public Long getSize(){
        return this.size;
    }
    public void setSize(Long size){
        this.size = size;
    }

    public String getDbId(){
        return this.dbId;
    }
    public void setDbId(String dbId){
        this.dbId = dbId;
    }

    public String getMd5(){
        return this.md5;
    }
    public void setMd5(String md5){
        this.md5 = md5;
    }

    public Integer getSeq(){
        return this.seq;
    }
    public void setSeq(Integer seq){
        this.seq = seq;
    }

    @Override
    public String toString() {
        return "CloudFileDO{" +
                "fileId='" + fileId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", createTime=" + createTime +
                ", type='" + type + '\'' +
                ", contentType='" + contentType + '\'' +
                ", size=" + size +
                ", dbId='" + dbId + '\'' +
                ", md5='" + md5 + '\'' +
                ", seq=" + seq +
                '}';
    }
}