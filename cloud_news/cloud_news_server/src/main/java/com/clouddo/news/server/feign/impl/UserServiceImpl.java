package com.clouddo.news.server.feign.impl;

import com.cloudd.commons.auth.model.User;
import com.clouddo.commons.common.util.message.Result;
import com.clouddo.news.server.feign.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * UserService 熔断器
 * @author zhongming
 * @since 3.0
 * 2018/5/29下午2:55
 */
@Component
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public Result<User> validate(Map<String, Object> parameters) {
        logger.error("调用{}异常{}", "validate", parameters);

        return Result.failure("调用validate接口异常");
    }


    @Override
    public Result<Set<String>> getPermissions(String userId) {
        logger.error("通过用户ID获取权限信息异常，调用方法：{}，用户id：{}", "getPermissions", userId);
        return Result.failure("通过用户ID获取权限信息异常");
    }

    @Override
    public Result<List<User>> list(Map<String, Object> parameters) {
        logger.error("查询用户列表失败，参数：{}", parameters);
        return null;
    }
}
