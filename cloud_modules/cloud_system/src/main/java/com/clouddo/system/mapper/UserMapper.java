package com.clouddo.system.mapper;

import com.cloudd.commons.auth.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 用户mapper层
 * @author zhongming
 * @since 3.0
 * 2018/5/4下午5:00
 */
@Mapper
@Deprecated
public interface UserMapper {

    User get(String userId);

    List<User> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(User user);

    int update(User user);

    int remove(String userId);

    int batchRemove(String[] userIds);

    String[] listAllDept();
}
