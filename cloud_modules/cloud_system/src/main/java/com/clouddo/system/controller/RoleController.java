package com.clouddo.system.controller;

import com.cloudd.commons.auth.controller.AuthController;
import com.clouddo.commons.common.constatns.CommonConstants;
import com.clouddo.commons.common.util.message.R;
import com.clouddo.commons.common.util.message.Result;
import com.clouddo.log.common.annotation.Log;
import com.clouddo.system.model.Role;
import com.clouddo.system.service.RoleService;
import com.github.pagehelper.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
@RequestMapping("/sys/role/ole")
@Controller
public class RoleController extends AuthController<Role> {
	String prefix = "system/role";
	@Autowired
    RoleService roleService;


	@RequiresPermissions("sys:role:role")
	@GetMapping()
	String role() {
		return prefix + "/role";
	}

	@RequiresPermissions("sys:role:role")
	@PostMapping("/list")
	@ResponseBody()
	Result<Map<String, Object>> list(@RequestBody Map<String, Object> parameterSet) {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			//判断是否分页
            Page page = this.paging(parameterSet);
			List<Role> roles = roleService.list();
			data.put(ROWS, roles);
			if(page != null) {
				data.put(TOTAL, page.getTotal());
			}
			return Result.success(data);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(e.getMessage());
		}

	}

	@Log("添加角色")
	@RequiresPermissions("sys:role:add")
	@GetMapping("/add")
	String add() {
		return prefix + "/add";
	}

	@Log("编辑角色")
	@RequiresPermissions("sys:role:edit")
	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") String id, Model model) {
		Role roleDO = roleService.get(id);
		model.addAttribute("role", roleDO);
		return prefix + "/edit";
	}

	@Log("保存角色")
	@RequiresPermissions("sys:role:add")
	@PostMapping("/save")
	@ResponseBody()
	R save(Role role) {
		if (CommonConstants.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if (roleService.save(role) > 0) {
			return R.ok();
		} else {
			return R.error(1, "保存失败");
		}
	}

	@Log("更新角色")
	@RequiresPermissions("sys:role:edit")
	@PostMapping("/update")
	@ResponseBody()
    Result update(@RequestBody Role role) {
		try {
			role.setGmtModified(new Date());
			return Result.success(roleService.update(role));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure("更新角色失败", e.getMessage());
		}
	}

	@Log("删除角色")
	@RequiresPermissions("sys:role:remove")
	@PostMapping("/remove")
	@ResponseBody()
    R save(String id) {
		if (CommonConstants.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if (roleService.remove(id) > 0) {
			return R.ok();
		} else {
			return R.error(1, "删除失败");
		}
	}
	
	@RequiresPermissions("sys:role:batchRemove")
	@Log("批量删除角色")
	@PostMapping("/batchRemove")
	@ResponseBody
    R batchRemove(@RequestParam("ids[]") String[] ids) {
		if (CommonConstants.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		int r = roleService.batchremove(ids);
		if (r > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 查询角色信息
	 * TODO 权限未添加
	 * @param role
	 * @return
	 */
	@ResponseBody
	@PostMapping("/get")
	@Log("获取角色")
	@RequiresPermissions("sys:role:get")
	public Result get(@RequestBody Role role) {
		try {
			role = this.roleService.get(role.getRoleId());
			return Result.success(role);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(e.getMessage());
		}
	}

	/**
	 * 查询角色拥有的菜单ID
	 * @param roleId
	 * @return
	 */
	@PostMapping("/listMenuId/{roleId}")
	@ResponseBody
	public Result listMenuId(@PathVariable("roleId") String roleId) {
		try {
			return Result.success(this.roleService.listMenuIdByRole(roleId));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure("查询角色拥有的菜单ID失败", e.getMessage());
		}
	}
}
