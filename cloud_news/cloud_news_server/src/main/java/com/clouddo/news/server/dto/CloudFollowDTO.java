package com.clouddo.news.server.dto;

import com.cloudd.commons.auth.model.User;
import com.clouddo.news.server.model.CloudFollow;

import java.io.Serializable;

/**
 * @author zhongming
 * @since 3.0
 * 2018/8/7上午9:19
 */
public class CloudFollowDTO extends CloudFollow implements Serializable {

    private static final long serialVersionUID = 4139545494805579253L;

    private User createUser;

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    @Override
    public String toString() {
        return "CloudFollowDTO{" +
                "createUser=" + createUser +
                '}';
    }
}
