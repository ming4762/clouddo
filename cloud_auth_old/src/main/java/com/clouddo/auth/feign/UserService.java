package com.clouddo.auth.feign;

import com.cloudd.commons.auth.model.User;
import com.clouddo.auth.feign.impl.UserServiceImpl;
import com.clouddo.commons.common.util.message.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
