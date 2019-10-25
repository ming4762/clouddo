package com.clouddo.system.controller;

import com.cloudd.commons.auth.controller.AuthController;
import com.clouddo.commons.common.constatns.CommonConstants;
import com.clouddo.commons.common.model.Tree;
import com.clouddo.commons.common.util.UUIDGenerator;
import com.clouddo.commons.common.util.message.R;
import com.clouddo.commons.common.util.message.Result;
import com.clouddo.log.common.annotation.Log;
import com.clouddo.system.dto.MenuDTO;
import com.clouddo.system.model.Menu;
import com.clouddo.system.service.MenuService;
import com.github.pagehelper.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bootdo 1992lcg@163.com
 */
@RequestMapping("/sys/menu/old")
@Controller
public class MenuController extends AuthController {
	String prefix = "system/menu";
	@Autowired
    MenuService menuService;

	@RequiresPermissions("sys:menu:menu")
	@GetMapping()
	String menu(Model model) {
		return prefix+"/menu";
	}

	/**
	 * 获取菜单列表
	 * @param parameterSet
	 * @return
	 */
	@RequiresPermissions("sys:menu:menu")
	@com.cloudd.commons.auth.annotation.RequiresPermissions("sys:menu:menu")
	@RequestMapping("/list")
	@ResponseBody
	Result list(@RequestBody Map<String, Object> parameterSet) {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			//判断是否分页
			Page page = this.paging(parameterSet);

			List<MenuDTO> menus = menuService.list(parameterSet);
			data.put(ROWS, menus);
			if(page != null) {
				data.put(ROWS, page.getTotal());
			}
			return Result.success(data);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(e.getMessage());
		}
	}

	@Log("添加菜单")
	@RequiresPermissions("sys:menu:add")
	@GetMapping("/add/{pId}")
	String add(Model model, @PathVariable("pId") String pId) {
		model.addAttribute("pId", pId);
		if ("0".equals(pId)) {
			model.addAttribute("pName", "根目录");
		} else {
			model.addAttribute("pName", menuService.get(pId).getName());
		}
		return prefix + "/add";
	}

	@Log("编辑菜单")
	@RequiresPermissions("sys:menu:edit")
	@GetMapping("/edit/{id}")
	String edit(Model model, @PathVariable("id") String id) {
		Menu mdo = menuService.get(id);
		String pId = mdo.getParentId();
		model.addAttribute("pId", pId);
		if ("0".equals(pId)) {
			model.addAttribute("pName", "根目录");
		} else {
			model.addAttribute("pName", menuService.get(pId).getName());
		}
		model.addAttribute("menu", mdo);
		return prefix+"/edit";
	}

	@Log("保存菜单")
	@RequiresPermissions("sys:menu:add")
	@PostMapping("/save")
	@ResponseBody
	R save(Menu menu) {
		if (CommonConstants.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if (menuService.save(menu) > 0) {
			return R.ok();
		} else {
			return R.error(1, "保存失败");
		}
	}

	@Log("保存或更新菜单")
	@PostMapping("/saveUpdate")
	@com.cloudd.commons.auth.annotation.RequiresPermissions("sys:menu:saveUpdate")
	@ResponseBody
	public Result saveUpdate(@RequestBody Menu menu) {
		try{
			Map<String, Object> result = new HashMap<String, Object>();
			if(StringUtils.isEmpty(menu.getMenuId())) {
				menu.setMenuId(UUIDGenerator.getUUID());
				menu.setGmtCreate(new Date());
				result.put("number", this.menuService.save(menu));
				result.put("message", "保存成功");
			} else {
				Menu menu1 = this.menuService.get(menu.getMenuId());
				if(menu1 == null) {
					menu.setGmtCreate(new Date());
					result.put("number", this.menuService.save(menu));
					result.put("message", "保存成功");
				} else {
					result.put("number", this.menuService.update(menu));
					result.put("message", "更新成功");
				}
			}
			return Result.success(result);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.success(e.getMessage());
		}
	}

	@Log("更新菜单")
	@RequiresPermissions("sys:menu:edit")
	@PostMapping("/update")
	@ResponseBody
    R update(Menu menu) {
		if (CommonConstants.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if (menuService.update(menu) > 0) {
			return R.ok();
		} else {
			return R.error(1, "更新失败");
		}
	}

	@Log("删除菜单")
	@RequiresPermissions("sys:menu:remove")
	@PostMapping("/remove")
	@ResponseBody
    R remove(String id) {
		if (CommonConstants.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if (menuService.remove(id) > 0) {
			return R.ok();
		} else {
			return R.error(1, "删除失败");
		}
	}

	@GetMapping("/tree")
	@ResponseBody
	Tree<Menu> tree() {
		Tree<Menu> tree = new Tree<Menu>();
		tree = menuService.getTree();
		return tree;
	}

	@GetMapping("/tree/{roleId}")
	@ResponseBody
	Tree<Menu> tree(@PathVariable("roleId") String roleId) {
		Tree<Menu> tree = new Tree<Menu>();
		tree = menuService.getTree(roleId);
		return tree;
	}

	/**
	 * 通过用户ID获取权限信息
	 * @param userId
	 * @return
	 */
	@PostMapping("/getPermissions")
	@ResponseBody
//	@com.cloudd.commons.auth.annotation.RequiresPermissions("sys:menu:getPermissions")
	Result getPermissions(@RequestBody String userId) {
		try {
			return Result.success(this.menuService.listPerms(userId));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure("通过用户ID获取权限信息失败，userID：" + userId);
		}
	}

	/**
	 * 菜单查询接口
	 * @param menu
	 * @return
	 */
	@PostMapping("/get")
	@ResponseBody
	@com.cloudd.commons.auth.annotation.RequiresPermissions("sys:menu:get")
	public Result get(@RequestBody Menu menu) {
		try {
			return Result.success(this.menuService.get(menu.getMenuId()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(e.getMessage());
		}
	}

	/**
	 * 批量删除
	 * @param menuList
	 * @return
	 */
	@ResponseBody
	@PostMapping("/batchDelete")
	@Log("批量删除菜单")
	@com.cloudd.commons.auth.annotation.RequiresPermissions("sys:menu:batchDelete")
	public Result batchDelete(@RequestBody List<Menu> menuList) {
		try {
			return Result.success(this.menuService.batchDelete(menuList));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(e.getMessage());
		}
	}

	/**
	 * 获取菜单树形结构
	 * @param parameters 参数
	 * @return 菜单树形结构
	 */
	@ResponseBody
	@PostMapping("/tree")
	@com.cloudd.commons.auth.annotation.RequiresPermissions("sys:menu:tree")
	public Result tree(@RequestBody Map<String, Object> parameters) {
		try {
			return Result.success(this.menuService.getTree());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure("获取菜单树形结构失败", e.getMessage());
		}
	}
}
