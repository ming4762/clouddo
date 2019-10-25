package com.clouddo.auth.feign.impl;

import com.clouddo.auth.feign.UserService;
import com.clouddo.commons.common.util.message.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * @author zhongming
 * @since 3.0
 * 2018/5/29下午2:55
 */
@Component
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public Result validate(Map<String, Object> parameters) {
        logger.error("调用{}异常{}", "validate", parameters);

        return Result.failure("调用validate接口异常");
    }


    @Override
    public Result<Set<String>> getPermissions(String userId) {
        logger.error("通过用户ID获取权限信息异常，调用方法：{}，用户id：{}", "getPermissions", userId);
        return Result.failure("通过用户ID获取权限信息异常");
    }
}
