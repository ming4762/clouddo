package com.clouddo.messagebus.feign.impl;

import com.clouddo.log.common.model.LogModel;
import com.clouddo.messagebus.feign.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 日志feign服务熔断器
 * @author zhongming
 * @since 3.0
 * 2018/5/30上午11:15
 */
@Service
public class LogServiceImpl implements LogService {

    private static Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);

    @Override
    public void saveLog(LogModel logModel) {
        logger.error("无法保存日志，调用日志服务:【{}】发生错误, log：{}", "cloud-log-server", logModel);
    }
}
