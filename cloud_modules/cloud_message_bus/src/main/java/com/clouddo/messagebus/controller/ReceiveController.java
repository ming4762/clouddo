package com.clouddo.messagebus.controller;

import com.clouddo.commons.common.constatns.MqQueueConstant;
import com.clouddo.commons.common.util.message.Result;
import com.clouddo.log.common.model.LogModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 接收请求controller
 * 作为消息总线的中转中心
 * 所有发送到消息总线的信息统一由此发送
 * 其他微服务不在维持消息总线连接
 * @author zhongming
 * @since 3.0
 * 2018/8/7下午2:11
 */
@Controller
@RequestMapping("/receive")
public class ReceiveController {

    /**
     * 消息总线支持
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 接收日志信息，并将日志发送到消息总线
     * @param logModel
     * @return
     */
    @PostMapping("/saveLog")
    @ResponseBody
    public Result saveLog(@RequestBody LogModel logModel) {
        rabbitTemplate.convertAndSend(MqQueueConstant.LOG_QUEUE, logModel);
        return Result.success("日志已发送到消息总线");
    }
}
