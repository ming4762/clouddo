package com.clouddo.news.server.model;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * <p>实体类</p>
 * <p>Table: cloud_follow - 跟帖信息</p>
 *
 * @since 2018-08-07 08:17:31
 */
@Table(name="cloud_follow")
public class CloudFollow implements Serializable  {

    /** FOLLOW_ID - 跟帖ID */
	@Column(name = "FOLLOW_ID")
    private String followId;

    /** CONTENT - 内容 */
    @Column(name = "CONTENT")
    private String content;

    /** CREATE_TIME - 跟帖时间 */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /** COMMENT_ID - 评论ID */
    @Column(name = "COMMENT_ID")
    private String commentId;

    /** USER_ID - 用户ID */
    @Column(name = "USER_ID")
    private String userId;


    public String getFollowId(){
        return this.followId; 
    }
    public void setFollowId(String followId){
        this.followId = followId; 
    }

    public String getContent(){
        return this.content;
    }
    public void setContent(String content){
        this.content = content;
    }

    public Date getCreateTime(){
        return this.createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    public String getCommentId(){
        return this.commentId;
    }
    public void setCommentId(String commentId){
        this.commentId = commentId;
    }

    public String getUserId(){
        return this.userId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }
}