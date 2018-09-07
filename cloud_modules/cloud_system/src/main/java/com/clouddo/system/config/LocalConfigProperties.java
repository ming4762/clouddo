package com.clouddo.system.config;

import com.clouddo.system.util.YamlPropertyLoaderFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhongming
 * @since 3.0
 * 2018/9/5下午3:52
 */
@Component
@ConfigurationProperties(prefix = "local")
@PropertySource(value = {"classpath:${cloud.config.localPath}"}, factory = YamlPropertyLoaderFactory.class)
public class LocalConfigProperties {

    /**
     * 系统信息
     */
    private Map<String, Object> system = new HashMap<>();
    /**
     * 开发人员信息
     */
    private Map<String, Object> development;


    public Map<String, Object> getSystem() {
        return system;
    }

    public void setSystem(Map<String, Object> system) {
        this.system = system;
    }

    public Map<String, Object> getDevelopment() {
        return development;
    }

    public void setDevelopment(Map<String, Object> development) {
        this.development = development;
    }
}
