package com.clouddo.messagebus.listener;

import com.clouddo.commons.common.constatns.MqQueueConstant;
import com.clouddo.log.common.model.LogModel;
import com.clouddo.messagebus.feign.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 日志消息队列监听
 * @author zhongming
 * @since 3.0
 * 2018/5/30上午11:08
 */
@Component
@RabbitListener(queues = MqQueueConstant.LOG_QUEUE)
public class LogReceiveListener {

    private static Logger logger = LoggerFactory.getLogger(LogReceiveListener.class);

    @Autowired
    private LogService logService;

    @RabbitHandler
    public void receive(LogModel logModel) {
        logger.info("接收到日志保存请求开始保存日志");
        logService.saveLog(logModel);
    }
}
