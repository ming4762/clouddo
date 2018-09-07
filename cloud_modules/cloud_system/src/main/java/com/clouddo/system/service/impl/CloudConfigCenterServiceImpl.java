package com.clouddo.system.service.impl;

import com.clouddo.system.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 配置服务类
 * @author zhongming
 * @since 3.0
 * 2018/9/5上午10:07
 */
@Service(value = "cloudConfigCenterServiceImpl")
public class CloudConfigCenterServiceImpl implements ConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CloudConfigCenterServiceImpl.class);

    private DiscoveryClient discoveryClient;

    /**
     * 配置信息
     */
    private static Map<String, Object> configMap;
    /**
     * 配置中心服务名
     */
    @Value("${spring.cloud.config.discovery.service-id}")
    private String configCenterName;

    @Autowired
    CloudConfigCenterServiceImpl(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    /**
     * 读取配置
     * @return
     */
    @Override
    public Object readConfig() {
//        if (configMap != null) {
//            return configMap;
//        }
//        // 获取配置中心信息
//        List<ServiceInstance> serviceInstanceList = this.discoveryClient.getInstances(configCenterName);
//        Object result = null;
//        if (serviceInstanceList != null && serviceInstanceList.size() > 0) {
//            for (ServiceInstance serviceInstance : serviceInstanceList) {
//                StringBuilder stringBuilder = new StringBuilder(serviceInstance.getUri().toString())
//                        .append(this.localConfigProperties.getName())
//                        .append("/")
//                        .append(this.localConfigProperties.getProfile())
//                        .append("/")
//                        .append(this.localConfigProperties.getLabel());
//                try {
//                    ResponseEntity responseEntity = RestUtil.restGet(stringBuilder.toString(), null, null);
//                    if (responseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
//                        result = responseEntity.getBody();
//                        break;
//                    } else {
//                        LOGGER.warn("从配置中心获取配置信息失败，url：{}", stringBuilder);
//                    }
//                } catch (Exception e) {
//                    LOGGER.error("从配置中心获取配置信息发生错误，url：{}，错误信息：{}", stringBuilder, e.getMessage());
//                }
//            }
//        }
//        if (result != null && result instanceof Map) {
//            Map<String, Object> resultMap = (Map<String, Object>) result;
//            try {
//                return (Map<String, Object>) ((Map<String, Object>)((Map<String, Object>)resultMap.get("propertySources")).get("propertySources")).get("source");
//            } catch (Exception e) {
//                e.printStackTrace();
//                LOGGER.error("解析配置信息失败", e);
//            }
//        }
        return null;
    }
}
