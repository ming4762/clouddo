package com.cloudd.commons.auth.interceptor;

import com.cloudd.commons.auth.annotation.RequiresPermissions;
import com.cloudd.commons.auth.config.UserAuthConfig;
import com.cloudd.commons.auth.model.JWTUser;
import com.cloudd.commons.auth.session.Session;
import com.cloudd.commons.auth.util.JWTHelper;
import com.cloudd.commons.auth.util.Logical;
import com.cloudd.commons.auth.util.SessionUtil;
import com.clouddo.commons.common.constatns.CommonConstants;
import com.clouddo.commons.common.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * 用户鉴权
 * TODO 代码与ServiceAuthRestInterceptor相似度很高，待重构
 * @author zhongming
 * @since 3.0
 * 2018/5/24下午3:27
 */
public class UserAuthRestInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(UserAuthRestInterceptor.class);

    /**
     * 是否是开发模式
     */
    @Value("${auth.development:false}")
    private boolean development;

    @Autowired
    private UserAuthConfig userAuthConfig;

    @Autowired
    private RedisService redisService;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //如果是开发模式 不拦截
        if(this.development) {
            return super.preHandle(request, response, handler);
        }

        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //获取方法上的注解RequiresPermissions，获取所需权限
            RequiresPermissions requiresPermissions = handlerMethod.getMethodAnnotation(RequiresPermissions.class);
            //如果没有注解，则不拦截
            if(requiresPermissions == null) {
                return super.preHandle(request, response, handler);
            }

            String token = request.getHeader(userAuthConfig.getTokenHeader());

            if(StringUtils.isEmpty(token)) {
                logger.warn("该请求需要权限：{}，token不能为空", requiresPermissions.value());
                return this.authErrorMessage("该请求需要权限，token不能为空", response);
            }

            JWTUser jwtInfo = null;
            try {
                jwtInfo = JWTHelper.getInfoFromToken(token, userAuthConfig.getPubKeyPath());
            } catch (Exception e) {
                e.printStackTrace();
                logger.warn("token解析失败，token:{}",token);
                return this.authErrorMessage("token解析失败", response);

            }
            //获取session
            Session session = (Session) redisService.get(SessionUtil.getSessionId(token));
            if(session == null) {
                logger.warn("token认证无效，未找到session，token:{}",token);
                return this.authErrorMessage("token认证无效", response);

            }
            SessionUtil.setUserSession(session);
            //token解密信息与session信息是否一致
            if(!authTokenSession(jwtInfo)) {
                logger.warn("token认证无效，session中存储的token信息与token解析信息不一致，token:{}",token);
                return this.authErrorMessage("token认证无效", response);

            }

            //获取权限集合
            String[] permissions = requiresPermissions.value();
            if(permissions.length == 0) {
                return super.preHandle(request, response, handler);
            }
            //获取权限是全部拥有才可访问还是满足其中一条就可访问
            Logical logical = requiresPermissions.logical();
            boolean needAll = true; //标识是否需要所有权限
            if(logical.name().equals("OR")) {
                needAll = false;
            }

            if(needAll) {
                if(this.hasAllPermissions(permissions)) {
                    return super.preHandle(request, response, handler);
                }
            } else {
                if(this.hasOnePermission(permissions)) {
                    return super.preHandle(request, response, handler);
                }
            }
            logger.warn("当前用户没有权限访问，token:{}",token);
            return this.authErrorMessage("当前用户没有权限访问", response);
        }

        return super.preHandle(request, response, handler);
    }


    /**
     * 是否含有其中一项权限
     * @param permissions
     * @return
     */
    private boolean hasOnePermission(String[] permissions) {
        //从session中获取用户拥有的所有权限
        List<String> userPermissions = (List<String>) SessionUtil.getUserSession().getAttribute(CommonConstants.USER_PERMISSIONS);
        if(userPermissions != null) {
            for(String permission : permissions) {
                if(userPermissions.contains(permission)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否含有所有权限
     * @return
     */
    private boolean hasAllPermissions(String[] permissions) {
        //从session中获取用户拥有的所有权限
        Set<String> userPermissions = (Set<String>) SessionUtil.getUserSession().getAttribute(CommonConstants.USER_PERMISSIONS);
        if(userPermissions != null) {
            for(String permission : permissions) {
                if(!userPermissions.contains(permission)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean authErrorMessage(String message, HttpServletResponse response) throws IOException {
        response.sendError(401, message);
        return false;
    }

    //认证token解密信息与session存储信息是否一致
    private boolean authTokenSession(JWTUser jwtInfo) {
        String username = jwtInfo.getUsername();
        JWTUser jwtInfoSession = (JWTUser) SessionUtil.getUserSession().getAttribute(CommonConstants.CONTEXT_KEY_JWTUSER);
        return username.equals(jwtInfoSession == null ? "" : jwtInfoSession.getUsername());
    }
}
