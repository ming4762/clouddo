package com.clouddo.monitor.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhongming
 * @since 3.0
 * 2018/8/30上午8:45
 */
@ConfigurationProperties("cloud.monitor")
@Component
public class MonitorConfig {

    private Error error = new Error();
    private Push push = new Push();

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Push getPush() {
        return push;
    }

    public void setPush(Push push) {
        this.push = push;
    }

    /**
     * 错误信息
     */
    public static class Error {
        private Integer limit;

        public Integer getLimit() {
            return limit;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }
    }

    /**
     * 推送设置
     */
    public static class Push {
        private String cron = "0 0 0 1/1 * ?";


        public String getCron() {
            return cron;
        }

        public void setCron(String cron) {
            this.cron = cron;
        }
    }
}
