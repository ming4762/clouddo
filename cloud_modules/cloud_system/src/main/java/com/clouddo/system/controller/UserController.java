package com.clouddo.system.controller;

import com.cloudd.commons.auth.controller.AuthController;
import com.cloudd.commons.auth.model.User;
import com.clouddo.commons.common.constatns.CommonConstants;
import com.clouddo.commons.common.model.Tree;
import com.clouddo.commons.common.util.message.R;
import com.clouddo.commons.common.util.message.Result;
import com.clouddo.commons.common.util.security.MD5Utils;
import com.clouddo.log.common.annotation.Log;
import com.clouddo.system.model.Dept;
import com.clouddo.system.model.Role;
import com.clouddo.system.service.RoleService;
import com.clouddo.system.service.UserService;
import com.clouddo.system.vo.UserVO;
import com.github.pagehelper.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/sys/user")
@Controller
public class UserController extends AuthController<User> {
	private String prefix="system/user"  ;
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
//	@Autowired
//	DictService dictService;
	@RequiresPermissions("sys:user:user")
	@GetMapping("")
	String user(Model model) {
		return prefix + "/user";
	}

	@PostMapping("/list")
	@ResponseBody
	Result<Map<String, Object>> list(@RequestBody Map<String, Object> parameterSet) {
		// 查询列表数据
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			//判断是否分页
			Page page = this.paging(parameterSet);
			List<User> userList = userService.list(parameterSet);
			data.put(ROWS, userList);
			if(page != null) {
				data.put(TOTAL, page.getTotal());
			}
			return Result.success(data);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(e.getMessage());
		}
	}

	/**
	 * 查询用户列表
	 * @param parameters
	 * @return
	 */
//	@PostMapping("/list")
	@ResponseBody
	public Result<List<User>> listPost(@RequestBody Map<String, Object> parameters) {
		try {
			return Result.success(this.userService.list(parameters));
		} catch (Exception e) {
			e.printStackTrace();
			//TODO 异常被吃掉，有可能对feign产生影响 待测试
			return Result.failure(e.getMessage());
		}
	}

	/**
	 * 验证用户
	 * @param parameters
	 * @return
	 */
	@ResponseBody
	@PostMapping("/validate")
	@Log("用户登录")
	public Result validate(@RequestBody Map<String, Object> parameters) {
		if(StringUtils.isEmpty(parameters.get("username"))) {
			return null;
		}
		if(StringUtils.isEmpty(parameters.get("password"))) {
			return null;
		}
		List<User> users = this.userService.list(parameters);
		if(users.size() == 1) {
			return Result.success(users.get(0));
		}
		return null;
	}

	@RequiresPermissions("sys:user:add")
	@Log("添加用户")
	@GetMapping("/add")
	String add(Model model) {
		List<Role> roles = roleService.list();
		model.addAttribute("roles", roles);
		return prefix + "/add";
	}

	@RequiresPermissions("sys:user:edit")
	@Log("编辑用户")
	@GetMapping("/edit/{id}")
	String edit(Model model, @PathVariable("id") String id) {
		User userDO = userService.get(id);
		model.addAttribute("user", userDO);
		List<Role> roles = roleService.list(id);
		model.addAttribute("roles", roles);
		return prefix+"/edit";
	}

	@RequiresPermissions("sys:user:add")
	@Log("保存用户")
	@PostMapping("/save")
	@ResponseBody
	R save(User user) {
		if (CommonConstants.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
		if (userService.save(user) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@RequiresPermissions("sys:user:edit")
	@Log("更新用户")
	@PostMapping("/update")
	@ResponseBody
    R update(User user) {
		if (CommonConstants.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if (userService.update(user) > 0) {
			return R.ok();
		}
		return R.error();
	}


	@RequiresPermissions("sys:user:edit")
	@Log("更新用户")
	@PostMapping("/updatePeronal")
	@ResponseBody
    R updatePeronal(User user) {
		if (CommonConstants.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if (userService.updatePersonal(user) > 0) {
			return R.ok();
		}
		return R.error();
	}


	@RequiresPermissions("sys:user:remove")
	@Log("删除用户")
	@PostMapping("/remove")
	@ResponseBody
    R remove(String id) {
		if (CommonConstants.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if (userService.remove(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@RequiresPermissions("sys:user:batchRemove")
	@Log("批量删除用户")
	@PostMapping("/batchRemove")
	@ResponseBody
    R batchRemove(@RequestParam("ids[]") String[] userIds) {
		if (CommonConstants.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		int r = userService.batchremove(userIds);
		if (r > 0) {
			return R.ok();
		}
		return R.error();
	}

	@PostMapping("/exit")
	@ResponseBody
	boolean exit(@RequestParam Map<String, Object> params) {
		// 存在，不通过，false
		return !userService.exit(params);
	}

	@RequiresPermissions("sys:user:resetPwd")
	@Log("请求更改用户密码")
	@GetMapping("/resetPwd/{id}")
	String resetPwd(@PathVariable("id") String userId, Model model) {

		User userDO = new User();
		userDO.setUserId(userId);
		model.addAttribute("user", userDO);
		return prefix + "/reset_pwd";
	}

	@Log("提交更改用户密码")
	@PostMapping("/resetPwd")
	@ResponseBody
    R resetPwd(UserVO userVO) {
		if (CommonConstants.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		try{
			userService.resetPwd(userVO,getUser());
			return R.ok();
		}catch (Exception e){
			return R.error(1,e.getMessage());
		}

	}
	@RequiresPermissions("sys:user:resetPwd")
	@Log("admin提交更改用户密码")
	@PostMapping("/adminResetPwd")
	@ResponseBody
    R adminResetPwd(UserVO userVO) {
		if (CommonConstants.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		try{
			userService.adminResetPwd(userVO);
			return R.ok();
		}catch (Exception e){
			return R.error(1,e.getMessage());
		}

	}
	@GetMapping("/tree")
	@ResponseBody
	public Tree<Dept> tree() {
		Tree<Dept> tree = new Tree<Dept>();
		tree = userService.getTree();
		return tree;
	}

	@GetMapping("/treeView")
	String treeView() {
		return  prefix + "/userTree";
	}

	@GetMapping("/personal")
	String personal(Model model) {
		User userDO  = userService.get(getUserId());
		model.addAttribute("user",userDO);
		//TODO 带处理
//		model.addAttribute("hobbyList",dictService.getHobbyList(userDO));
//		model.addAttribute("sexList",dictService.getSexList());
		return prefix + "/personal";
	}
	@ResponseBody
	@PostMapping("/uploadImg")
    R uploadImg(@RequestParam("avatar_file") MultipartFile file, String avatar_data, HttpServletRequest request) {
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		Map<String, Object> result = new HashMap<>();
		try {
			result = userService.updatePersonalImg(file, avatar_data, getUserId());
		} catch (Exception e) {
			return R.error("更新图像失败！");
		}
		if(result!=null && result.size()>0){
			return R.ok(result);
		}else {
			return R.error("更新图像失败！");
		}
	}
}
