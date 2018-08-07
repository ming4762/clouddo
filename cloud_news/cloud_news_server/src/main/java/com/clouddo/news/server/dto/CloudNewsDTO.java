package com.clouddo.news.server.dto;

import com.cloudd.commons.auth.model.User;
import com.clouddo.news.server.model.CloudNews;

import java.io.Serializable;
import java.util.List;

/**
 * 新闻传输对象
 * @author zhongming
 * @since 3.0
 * 2018/8/2下午4:30
 */
public class CloudNewsDTO extends CloudNews implements Serializable {

    private static final long serialVersionUID = -5891526921819870148L;

    /**
     * 创建人
     */
    private User author;

    /**
     * 修改人
     */
    private User modifier;

    /**
     * 评论信息
     */
    private List<CloudCommentDTO> cloudCommentDTOList;


    public User getModifier() {
        return modifier;
    }

    public void setModifier(User modifier) {
        this.modifier = modifier;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<CloudCommentDTO> getCloudCommentDTOList() {
        return cloudCommentDTOList;
    }

    public void setCloudCommentDTOList(List<CloudCommentDTO> cloudCommentDTOList) {
        this.cloudCommentDTOList = cloudCommentDTOList;
    }
}
