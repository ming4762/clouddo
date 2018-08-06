package com.clouddo.system.service;

import com.cloudd.commons.auth.model.User;
import com.clouddo.commons.common.model.Tree;
import com.clouddo.system.model.Dept;
import com.clouddo.system.vo.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public interface UserService {
	User get(String id);

	List<User> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(User user);

	int update(User user);

	int remove(String userId);

	int batchremove(String[] userIds);

	boolean exit(Map<String, Object> params);

	Set<String> listRoles(String userId);

	int resetPwd(UserVO userVO, User User) throws Exception;
	int adminResetPwd(UserVO userVO) throws Exception;
	Tree<Dept> getTree();

	/**
	 * 更新个人信息
	 * @param User
	 * @return
	 */
	int updatePersonal(User User);

	/**
	 * 更新个人图片
	 * @param file 图片
	 * @param avatar_data 裁剪信息
	 * @param userId 用户ID
	 * @throws Exception
	 */
    Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, String userId) throws Exception;
}
