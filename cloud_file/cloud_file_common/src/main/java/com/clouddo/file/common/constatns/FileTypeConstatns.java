package com.clouddo.file.common.constatns;

/**
 * @author zhongming
 * @since 3.0
 * 2018/7/25上午11:07
 */
public enum FileTypeConstatns {

    /**
     * 普通文件
     */
    NORMAL("normal"),
    /**
     * 测试文件
     */
    TEST("test");

    private String value;

    private FileTypeConstatns(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
