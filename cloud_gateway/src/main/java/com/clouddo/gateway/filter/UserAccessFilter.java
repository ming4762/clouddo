package com.clouddo.gateway.filter;

import com.cloudd.commons.auth.config.UserAuthConfig;
import com.cloudd.commons.auth.constatns.AuthConstants;
import com.cloudd.commons.auth.model.JWTUser;
import com.cloudd.commons.auth.session.Session;
import com.cloudd.commons.auth.util.JWTHelper;
import com.cloudd.commons.auth.util.SessionUtil;
import com.clouddo.commons.common.constatns.CommonConstants;
import com.clouddo.commons.common.service.RedisService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户身份认证
 * @author zhongming
 * @since 3.0
 * 2018/5/24下午4:20
 */
@Component
public class UserAccessFilter extends ZuulFilter {

    private static Logger logger = LoggerFactory.getLogger(UserAccessFilter.class);

    /**
     * 是否是开发模式
     */
    @Value("${auth.development:false}")
    private boolean development;

    /**
     * 忽略权限访问的请求头
     */
    @Value("${gate.ignore.startWith}")
    private String ignoreStartWith;

    @Value("${zuul.prefix}")
    private String zuulPrefix;

    @Autowired
    private UserAuthConfig userAuthConfig;

    @Autowired
    private RedisService redisService;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //如果是开发模式，不拦截
        if(this.development) {
            return null;
        }

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        final String requestUri = request.getRequestURI().substring(zuulPrefix.length());
        if(request.getMethod().equals(HttpMethod.OPTIONS.name())) {
            return null;
        }

        // 不进行拦截的地址
        if (isStartWith(requestUri)) {
            return null;
        }

        String token = request.getHeader(userAuthConfig.getTokenHeader());
        JWTUser jwtInfo = null;
        try {
            jwtInfo = JWTHelper.getInfoFromToken(token, userAuthConfig.getPubKeyPath());
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("token解密失败，token：{}", token);
            this.setFailedRequest("token解析失败", AuthConstants.EX_TOKEN_ERROR_CODE);
            return null;
        }

        //获取session，并设置到全局
        Session session = (Session) redisService.get(SessionUtil.getSessionId(token));
        SessionUtil.setUserSession(session);
        if(session == null) {
            logger.warn("token认证失败，未找到session，token：{}", token);
            this.setFailedRequest("token认证无效", AuthConstants.EX_TOKEN_ERROR_CODE);
            return null;
        }
        //token解密信息与session信息是否一致
        if(!authTokenSession(jwtInfo)) {
            logger.warn("token认证失败，session信息与token解密信息不一致，token：{}", token);
            this.setFailedRequest("token认证无效", AuthConstants.EX_TOKEN_ERROR_CODE);
        }
        return null;
    }

    /**
     * 网关抛异常
     *
     * @param body
     * @param code
     */
    private void setFailedRequest(String body, int code) {
        logger.debug("Reporting error ({}): {}", code, body);
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.getResponse().setContentType("text/html;charset=UTF-8");
        ctx.setResponseStatusCode(code);
        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody(body);
            ctx.setSendZuulResponse(false);
        }
    }

    /**
     * 认证token解密信息与session存储信息是否一致
     * @param jwtInfo
     * @return
     */
    private boolean authTokenSession(JWTUser jwtInfo) {
        String username = jwtInfo.getUsername();
        JWTUser jwtInfoSession = (JWTUser) SessionUtil.getUserSession().getAttribute(CommonConstants.CONTEXT_KEY_JWTUSER);
        return username.equals(jwtInfoSession == null ? "" : jwtInfoSession.getUsername());
    }

    /**
     * URI是否以什么打头
     *
     * @param requestUri
     * @return
     */
    private boolean isStartWith(String requestUri) {
        boolean flag = false;
        for (String s : ignoreStartWith.split(",")) {
            if (requestUri.startsWith(s)) {
                return true;
            }
        }
        return flag;
    }
}