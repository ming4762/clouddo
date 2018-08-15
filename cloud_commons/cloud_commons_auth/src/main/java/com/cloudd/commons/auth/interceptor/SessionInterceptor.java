package com.cloudd.commons.auth.interceptor;

import com.cloudd.commons.auth.config.UserAuthConfig;
import com.cloudd.commons.auth.session.Session;
import com.cloudd.commons.auth.util.SessionUtil;
import com.clouddo.commons.common.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhongming
 * @since 3.0
 * 2018/5/29下午1:26
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);

    @Autowired
    private UserAuthConfig userAuthConfig;
    @Autowired
    private RedisService redisService;

    /**
     * 请求前执行设置token
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取token
        String token = request.getHeader(userAuthConfig.getTokenHeader());
        if(!StringUtils.isEmpty(token)) {
            //从redis中获取session信息
            Session session = (Session) redisService.get(SessionUtil.getSessionId(token));
            SessionUtil.setUserSession(session);
        }

        return super.preHandle(request, response, handler);
    }

    /**
     * 保存token
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //保存session
        if(SessionUtil.getUserSession() != null) {
            this.redisService.put(SessionUtil.getSessionId(SessionUtil.getUserSession().getId().toString()), SessionUtil.getUserSession(), userAuthConfig.getTokenExpire());
        }
        super.afterCompletion(request, response, handler, ex);
    }

}
