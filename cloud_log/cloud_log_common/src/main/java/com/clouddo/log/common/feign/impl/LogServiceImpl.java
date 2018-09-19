package com.clouddo.log.common.feign.impl;

import com.clouddo.commons.common.util.message.Result;
import com.clouddo.log.common.feign.LogService;
import com.clouddo.log.common.model.LogModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 日志服务熔断器
 * @author zhongming
 * @since 3.0
 * 2018/8/7下午2:25
 */
@Component
public class LogServiceImpl implements LogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogServiceImpl.class);

    @PostConstruct
    private void init() {
        System.out.println("创建了=======================");
    }

    /**
     * 保存日志
     * @param logModel
     * @return
     */
    @Override
    public Result save(LogModel logModel) {
        LOGGER.error("保存日志时发生错误，日志信息：", logModel.toString());
        return Result.failure("保存日志发生错误");
    }
}
