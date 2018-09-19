package com.clouddo.monitor.server.constatns;

/**
 * @author zhongming
 * @since 3.0
 * 2018/8/30下午2:24
 */
public enum  MonitorConstatns {

    /**
     * 错误信息
     */
    ERROR("MONITOR_ERROR"),
    // 警告信息
    WARNING("MONITOR_WARNING"),
    // 正常信息
    NORMAL("MONITOR_NORMAL");

    private String value;
    private MonitorConstatns(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
