package com.clouddo.news.server.model;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * <p>实体类</p>
 * <p>Table: cloud_comment - 评论信息</p>
 *
 * @since 2018-08-06 05:31:32
 */
@Table(name="cloud_comment")
public class CloudComment implements Serializable  {

    private static final long serialVersionUID = -5171313446329194285L;
    /** COMMENT_ID - 评论ID */
	@Column(name = "COMMENT_ID")
    private String commentId;

    /** CONTENT - 评论内容 */
    @Column(name = "CONTENT")
    private String content;

    /** CREATE_TIME - 评论时间 */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /** USER_ID - 评论人ID */
    @Column(name = "USER_ID")
    private String userId;

    /** PRAISE_NUM - 点赞数 */
    @Column(name = "PRAISE_NUM")
    private Integer praiseNum;

    /** NEWS_ID - 新闻ID */
    @Column(name = "NEWS_ID")
    private String newsId;


    public String getCommentId(){
        return this.commentId; 
    }
    public void setCommentId(String commentId){
        this.commentId = commentId; 
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

    public String getUserId(){
        return this.userId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }

    public Integer getPraiseNum(){
        return this.praiseNum;
    }
    public void setPraiseNum(Integer praiseNum){
        this.praiseNum = praiseNum;
    }

    public String getNewsId(){
        return this.newsId;
    }
    public void setNewsId(String newsId){
        this.newsId = newsId;
    }

    @Override
    public String toString() {
        return "CloudComment{" +
                "commentId='" + commentId + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", userId='" + userId + '\'' +
                ", praiseNum=" + praiseNum +
                ", newsId='" + newsId + '\'' +
                '}';
    }
}