package com.clouddo.system.dto;

import com.clouddo.system.model.Menu;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhongming
 * @since 3.0
 * 2018/8/9下午7:46
 */
public class MenuDTO extends Menu implements Serializable {

    private static final long serialVersionUID = 3820521575154829365L;

    /**
     * 下级菜单
     */
    private List<MenuDTO> children;

    /**
     * 是否是叶子节点
     */
    private Boolean isLeaf;

    public List<MenuDTO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuDTO> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "MenuDTO{" +
                "children=" + children +
                '}';
    }

    public Boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Boolean leaf) {
        isLeaf = leaf;
    }
}
