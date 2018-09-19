package com.clouddo.wechat.common.mp.config;

import com.clouddo.commons.common.util.GsonUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 微信公众号配置类
 * @author zhongming
 * @since 3.0
 * 2018/8/31上午8:31
 */
@ConfigurationProperties(prefix = "cloud.wechat.mp")
public class WeChatMpProperties {

    private Map<String, MpConfig> configs = new HashMap<String, MpConfig>();

    public Map<String, MpConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(Map<String, MpConfig> configs) {
        this.configs = configs;
    }

    @Override
    public String toString() {
        return GsonUtil.toJson(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        WeChatMpProperties that = (WeChatMpProperties) o;
        return Objects.equals(configs, that.configs);
    }

    @Override
    public int hashCode() {

        return Objects.hash(configs);
    }

    public static class MpConfig {
        /**
         * 设置微信公众号的appid
         */
        private String appid;

        /**
         * 设置微信公众号的app secret
         */
        private String secret;

        /**
         * 设置微信公众号的token
         */
        private String token;

        /**
         * 设置微信公众号的EncodingAESKey
         */
        private String aesKey;


        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAesKey() {
            return aesKey;
        }

        public void setAesKey(String aesKey) {
            this.aesKey = aesKey;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }
    }
}
