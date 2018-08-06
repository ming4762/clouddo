package com.clouddo.news.server.feign;

import com.cloudd.commons.auth.model.User;
import com.clouddo.commons.common.util.message.Result;
import com.clouddo.news.server.feign.impl.UserServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhongming
 * @since 3.0
 * 2018/5/29下午2:53
 */
@FeignClient(name = "cloud-system", fallback = UserServiceImpl.class)
public interface UserService {



    @PostMapping(value = "/sys/user/validate")
    @ResponseBody
    Result<User> validate(Map<String, Object> parameters);

    /**
     * 通过用户ID获取权限信息
     * @param userId
     * @return
     */
    @PostMapping(value = "/sys/menu/getPermissions")
    @ResponseBody
    Result<Set<String>> getPermissions(String userId);

    /**
     * 查询用户列表
     * @param parameters
     * @return
     */
    @PostMapping(value = "/sys/user/list")
    @ResponseBody
    Result<List<User>> list(Map<String, Object> parameters);
}
