package com.clouddo.system.controller;

import com.cloudd.commons.auth.controller.AuthController;
import com.clouddo.commons.common.model.Tree;
import com.clouddo.commons.common.util.message.Result;
import com.clouddo.log.common.annotation.Log;
import com.clouddo.system.model.Menu;
import com.clouddo.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主页controller
 * @author zhongming
 * @since 3.0
 * 2018/6/1上午10:11
 */
@RestController
@RequestMapping("/index")
public class IndexController extends AuthController {

    @Autowired
    private MenuService menuService;

    /**
     * 访问系统主页
     * TODO 带完善 从session中获取
     * @return
     */
    @PostMapping
    @Log("请求访问主页")
    Result index() {
        try {
            Map<String, Object> result = new HashMap<String, Object>();
            //获取菜单信息
            List<Tree<Menu>> menus = menuService.listMenuTree(getUserId());
            result.put("menus", menus);
            result.put("username", getUsername());
            result.put("picUrl","/img/photo_s.jpg");
            return Result.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure("请求访问主页发生错误", e.getMessage());
        }
    }
}
