package com.clouddo.news.server.model;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * <p>实体类</p>
 * <p>Table: cloud_news - 新闻</p>
 * @author zhongming
 * @since 2018-07-30 04:57:25
 */
@Table(name="cloud_news")
public class CloudNews implements Serializable  {

    private static final long serialVersionUID = 4597195532891665586L;
    /** NEWS_ID - 新闻ID */
	@Column(name = "NEWS_ID")
    private String newsId;

    /** TITLE - 新闻标题 */
    @Column(name = "TITLE")
    private String title;

    /** SUBTITLE - 副标题 */
    @Column(name = "SUBTITLE")
    private String subtitle;

    /** TITLE_PIC - 标题图片 */
    @Column(name = "TITLE_PIC")
    private String titlePic;

    /** AUTHOR_ID - 作者ID */
    @Column(name = "AUTHOR_ID")
    private String authorId;

    /** CREATE_TIME - 创建时间 */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /** MODIFIER_ID - 修改人ID */
    @Column(name = "MODIFIER_ID")
    private String modifierId;

    /** UPDATE_TIME - 修改时间 */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /** CONTENT - 内容 */
    @Column(name = "CONTENT")
    private String content;

    /** COLUMN_ID - 栏目ID */
    @Column(name = "COLUMN_ID")
    private String columnId;

    /** PRAISE_NUM - 点赞数 */
    @Column(name = "PRAISE_NUM")
    private Integer praiseNum;

    /** READ_NUM - 阅读数 */
    @Column(name = "READ_NUM")
    private Integer readNum;

    /** COMMENT_NUM - 评论数 */
    @Column(name = "COMMENT_NUM")
    private Integer commentNum;

    /**
     * 子模块ID
     */
    @Column(name = "SUBSECTION_ID")
    private String subsectionId;


    public String getNewsId(){
        return this.newsId; 
    }
    public void setNewsId(String newsId){
        this.newsId = newsId; 
    }

    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getSubtitle(){
        return this.subtitle;
    }
    public void setSubtitle(String subtitle){
        this.subtitle = subtitle;
    }

    public String getTitlePic(){
        return this.titlePic;
    }
    public void setTitlePic(String titlePic){
        this.titlePic = titlePic;
    }

    public String getAuthorId(){
        return this.authorId;
    }
    public void setAuthorId(String authorId){
        this.authorId = authorId;
    }

    public Date getCreateTime(){
        return this.createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    public String getModifierId(){
        return this.modifierId;
    }
    public void setModifierId(String modifierId){
        this.modifierId = modifierId;
    }

    public Date getUpdateTime(){
        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }

    public String getContent(){
        return this.content;
    }
    public void setContent(String content){
        this.content = content;
    }

    public String getColumnId(){
        return this.columnId;
    }
    public void setColumnId(String columnId){
        this.columnId = columnId;
    }

    public Integer getPraiseNum(){
        return this.praiseNum;
    }
    public void setPraiseNum(Integer praiseNum){
        this.praiseNum = praiseNum;
    }

    public Integer getReadNum(){
        return this.readNum;
    }
    public void setReadNum(Integer readNum){
        this.readNum = readNum;
    }

    public Integer getCommentNum(){
        return this.commentNum;
    }
    public void setCommentNum(Integer commentNum){
        this.commentNum = commentNum;
    }

    public String getSubsectionId() {
        return subsectionId;
    }

    public void setSubsectionId(String subsectionId) {
        this.subsectionId = subsectionId;
    }
}