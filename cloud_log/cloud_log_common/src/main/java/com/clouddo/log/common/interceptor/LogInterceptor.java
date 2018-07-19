package com.clouddo.log.common.interceptor;

import com.cloudd.commons.auth.model.User;
import com.cloudd.commons.auth.util.UserUtil;
import com.clouddo.commons.common.constatns.MqQueueConstant;
import com.clouddo.commons.common.model.LogModel;
import com.clouddo.commons.common.util.IPUtils;
import com.clouddo.log.common.annotation.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 日志拦截器
 * @author zhongming
 * @since 3.0
 * 2018/5/30上午8:24
 */
public class LogInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    //开始执行方法的时间
    private Long startTime;

    //消息总线支持
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //请求前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod) {
            //设置开始时间
            startTime = System.currentTimeMillis();
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //获取方法上的log注解
            Log log = handlerMethod.getMethodAnnotation(Log.class);
            if(log != null) {
                //记录请求内容
                logger.info("请求地址 : {}", request.getRequestURL().toString());
                logger.info("HTTP METHOD : {}", request.getMethod());
//                logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
//                        + joinPoint.getSignature().getName());
                //待完善
//                logger.info("参数 : {}" + Arrays.toString(handlerMethod.getMethodParameters()));
            }
        }
        return super.preHandle(request, response, handler);
    }

    //请求之后
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    //页面渲染之后
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //获取方法上的log注解
            Log log = handlerMethod.getMethodAnnotation(Log.class);
            if(log != null) {
                long time = System.currentTimeMillis() - startTime;
                logger.info("请求耗时 ：{}", time);
                //数据库保存日志
                saveLog(request, handlerMethod, time);
            }

        }
        if(ex != null) {
            logger.error("程序运行发生异常：{}", ex.getMessage());
        }
        super.afterCompletion(request, response, handler, ex);
    }

    //保存日志操作
    private void saveLog(HttpServletRequest request, HandlerMethod handlerMethod, Long time) throws JsonProcessingException {
        LogModel logModel = new LogModel();
        Log log = handlerMethod.getMethodAnnotation(Log.class);
        //设置描述
        logModel.setOperation(log.value());
        //设置请求方法
        logModel.setMethod(handlerMethod.getBeanType() + "." + handlerMethod.getMethod().getName() + "()");
        //设置请求参数
//        logModel.setParams(JSONUtils.beanToJson(handlerMethod.getMethodParameters()));
        //设置请求ID
        logModel.setIp(IPUtils.getIpAddr(request));
        //设置当前登录人员
        User user = UserUtil.getCurrentUser();
        if(user == null) {
            logModel.setUserId("-1");
            if(logModel.getParams() != null) {
                logModel.setUsername(logModel.getParams());
            } else {
                logModel.setUsername("获取用户信息为空");
            }
        } else {
            logModel.setUserId(user.getUserId());
            logModel.setUsername(user.getUsername());
        }
        logModel.setTime(time.intValue());
        logModel.setGmtCreate(new Date());

        //将日志保存操作发送到日志保存队列
        rabbitTemplate.convertAndSend(MqQueueConstant.LOG_QUEUE, logModel);
    }
}
