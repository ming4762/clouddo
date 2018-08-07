package com.clouddo.news.server.dto;

import com.cloudd.commons.auth.model.User;
import com.clouddo.news.server.model.CloudComment;

import java.io.Serializable;
import java.util.List;

/**
 * 评论数据传输对象
 * @author zhongming
 * @since 3.0
 * 2018/8/7上午9:12
 */
public class CloudCommentDTO extends CloudComment implements Serializable {

    private static final long serialVersionUID = -4369649478115249982L;

    /**
     * 跟帖信息
     */
    private List<CloudFollowDTO> cloudFollowList;

    /**
     * 评论人信息
     */
    private User createUser;

    public List<CloudFollowDTO> getCloudFollowList() {
        return cloudFollowList;
    }

    public void setCloudFollowList(List<CloudFollowDTO> cloudFollowList) {
        this.cloudFollowList = cloudFollowList;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }
}
