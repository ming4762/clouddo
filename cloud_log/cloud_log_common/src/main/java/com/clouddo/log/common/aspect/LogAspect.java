package com.clouddo.log.common.aspect;

import com.cloudd.commons.auth.model.User;
import com.cloudd.commons.auth.util.UserUtil;
import com.clouddo.commons.common.util.HttpContextUtils;
import com.clouddo.commons.common.util.IPUtils;
import com.clouddo.commons.common.util.JSONUtils;
import com.clouddo.log.common.annotation.Log;
import com.clouddo.log.common.feign.LogService;
import com.clouddo.log.common.model.LogModel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 日志切面
 * 所有需要发送日志的微服务需要此切面
 * @author zhongming
 * @since 3.0
 * 2018/8/7下午2:21
 */
@Aspect
public class LogAspect {

    @Autowired
    private LogService logService;

    /**
     * 切面
     */
    @Pointcut("@annotation(com.clouddo.log.common.annotation.Log)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //异步保存日志
        saveLog(point, time);
        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time) throws InterruptedException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogModel logModel = new LogModel();
        Log syslog = method.getAnnotation(Log.class);
        if (syslog != null) {
            // 注解上的描述
            logModel.setOperation(syslog.value());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        logModel.setMethod(className + "." + methodName + "()");
        // 请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = JSONUtils.beanToJson(args[0]);
            logModel.setParams(params);
        } catch (Exception e) {

        }
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        logModel.setIp(IPUtils.getIpAddr(request));
        // 用户名
        User currUser = UserUtil.getCurrentUser();
        if (null == currUser) {
            if (null != logModel.getParams()) {
                logModel.setUserId("-1");
                logModel.setUsername(logModel.getParams());
            } else {
                logModel.setUserId("-1");
                logModel.setUsername("获取用户信息为空");
            }
        } else {
            logModel.setUserId(currUser.getUserId());
            logModel.setUsername(currUser.getUsername());
        }
        logModel.setTime((int) time);
        // 系统当前时间
        Date date = new Date();
        logModel.setGmtCreate(date);
        // 保存系统日志
        logService.save(logModel);
    }
}
