package com.clouddo.commons.common.config;

import com.clouddo.commons.common.redis.RedisHeart;
import com.clouddo.commons.common.service.RedisService;
import com.clouddo.commons.common.service.impl.DefaultRedisServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * redis配置类
 * @author zhongming
 * @since 3.0
 * 2018/8/13上午10:28
 */
@Configuration
@EnableScheduling
public class RedisConfig {

    /**
     * redis服务配置
     * @param redisTemplate
     * @return
     */
    @Bean
    public RedisService redisService(RedisTemplate<Object, Object> redisTemplate) {
        return new DefaultRedisServiceImpl(redisTemplate);
    }

    /**
     * redis心跳解决redis长时间不连导致连接失效的问题
     * @return
     */
    @Bean
    public RedisHeart redisHeart() {
        return new RedisHeart();
    }
}
