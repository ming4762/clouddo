package com.clouddo.commons.common.util;

/**
 * @author zhongming
 * @since 3.0
 * 2018/8/27下午3:26
 */
public class ClassUtil {

    /**
     * 判断是否是继承关系
     * @param child
     * @param parent
     * @return
     */
    public static boolean isAssignableFrom(String child, Class parent) {
        try {
            Class childClass = Class.forName(child);
            return parent.isAssignableFrom(childClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

}
