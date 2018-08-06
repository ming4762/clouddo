package com.clouddo.news.server.dto;

import com.clouddo.news.server.model.CloudColumn;
import com.clouddo.news.server.model.CloudSubsection;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhongming
 * @since 3.0
 * 2018/7/31上午11:01
 */
public class CloudColumnDTO extends CloudColumn implements Serializable {

    private static final long serialVersionUID = -6141841509424617553L;

    /**
     * 栏目包含的子栏目
     */
    private List<CloudSubsection> cloudSubsectionList;


    public List<CloudSubsection> getCloudSubsectionList() {
        return cloudSubsectionList;
    }

    public void setCloudSubsectionList(List<CloudSubsection> cloudSubsectionList) {
        this.cloudSubsectionList = cloudSubsectionList;
    }


    @Override
    public String toString() {
        return "CloudColumnDTO{" +
                "cloudSubsectionList=" + cloudSubsectionList +
                '}';
    }
}
