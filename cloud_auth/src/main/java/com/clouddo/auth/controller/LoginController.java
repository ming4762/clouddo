package com.clouddo.auth.controller;

import com.cloudd.commons.auth.config.UserAuthConfig;
import com.cloudd.commons.auth.constatns.AuthConstants;
import com.cloudd.commons.auth.controller.AuthController;
import com.cloudd.commons.auth.model.User;
import com.cloudd.commons.auth.util.SessionUtil;
import com.cloudd.commons.auth.util.TokenUtil;
import com.clouddo.auth.feign.UserService;
import com.clouddo.commons.common.constatns.CommonConstants;
import com.clouddo.commons.common.util.message.Result;
import com.clouddo.commons.common.util.message.ResultCodeEnum;
import com.clouddo.commons.common.util.security.MD5Utils;
import com.clouddo.log.common.annotation.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author zhongming
 * @since 3.0
 * 2018/5/29下午2:30
 */
@RestController
@RequestMapping
public class LoginController extends com.cloudd.commons.auth.controller.AuthController {

    private static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserAuthConfig userAuthConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtil tokenUtil;

    /**
     * 登录接口
     * @param parameters
     * @return
     */
    @RequestMapping("/login")
    public Object login(@RequestBody Map<String, Object> parameters) {
        try {
            String username = (String) parameters.get("username");
            String password = (String) parameters.get("password");
            if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
                logger.warn("用户名或密码为空，username：{}  password：{}", username, password);
                return Result.Result(403, "用户名或密码不能为空");
            }
            password = MD5Utils.encrypt(username, password);
            parameters.put("password", password);
//            parameters.remove("password");
            //验证登录
            Result result = userService.validate(parameters);
            if(result != null && ResultCodeEnum.FAILURE.getCode().equals(result.getCode())) {
                logger.warn("调用后台接口异常，username：{}  password：{}", username, password);
                return Result.Result(500, result.getMessage());
            }
            if(result == null || result.getData() == null) {
                logger.warn("用户名或密码错误，username：{}  password：{}", username, password);
                return Result.Result(403, "用户名或密码错误");
            }

            //生成token
            User user = (User) result.getData();
            String token = tokenUtil.generateUserToken(user, userAuthConfig.getTokenExpire());
            Map<String, Object> resultdata = new HashMap<String, Object>();
            resultdata.put("token", token);
            //创建session
            SessionUtil.createSession(user, token);
            //保存权限信息
//            this.savePermissions(user.getUserId());
//            resultdata.put(CommonConstants.USER_PERMISSIONS, SessionUtil.getUserSession().getAttribute(CommonConstants.USER_PERMISSIONS));
            logger.info("登陆成功，username：{}  password：{}", username, password);
            return Result.success(resultdata);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("服务器发生错误，message：{}", e.getMessage());
            return Result.failure(500, "服务器发生错误", e.getMessage());
        }
    }

    /**
     * 登出接口
     * @param parameters
     * @return
     */
    @PostMapping("/logout")
    @Log("登出")
    public Result logout(@RequestBody Map<String, Object> parameters, HttpServletRequest request) {
        try {
            //移除session
            this.tokenUtil.deleteToken(request.getHeader(AuthConstants.TOKEN_KEY));
            SessionUtil.deleteSession();
            return Result.success("登出成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure("登出失败", e.getMessage());
        }
    }

    /**
     * 通过用户ID获取权限信息,并保存到session
     * @param userId
     */
    private void savePermissions(String userId) {
        Set<String> permissions = (Set<String>) this.userService.getPermissions(userId).getData();
        //将权限信息放入session
        SessionUtil.getUserSession().setAttribute(CommonConstants.USER_PERMISSIONS, permissions);
    }
}
