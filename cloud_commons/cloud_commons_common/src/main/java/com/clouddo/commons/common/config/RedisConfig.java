package com.clouddo.commons.common.config;

import com.clouddo.commons.common.redis.RedisHeart;
import com.clouddo.commons.common.service.RedisService;
import com.clouddo.commons.common.service.impl.DefaultRedisServiceImpl;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
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


    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
        redisTemplate.setConnectionFactory(factory);
        //修改key的序列化器
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        redisTemplate.setKeySerializer(jackson2JsonRedisSerializer);

        return redisTemplate;
    }

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
