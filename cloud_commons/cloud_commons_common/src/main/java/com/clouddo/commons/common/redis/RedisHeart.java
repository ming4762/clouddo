package com.clouddo.commons.common.redis;

import com.clouddo.commons.common.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * @author zhongming
 * @since 3.0
 * 2018/8/12下午8:19
 */
public class RedisHeart {

    private static final String HEART_KEY = "redis_heart";

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisHeart.class);

    @Autowired
    private RedisService redisService;

    @Scheduled(cron = "0 0/1 * * * ? ")
    public void redisHeart() {
        LOGGER.info("redisHeart：connect to redis，time：" + new Date());
        this.redisService.get("HEART_KEY");
    }
}
