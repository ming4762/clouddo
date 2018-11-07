package com.clouddo.system.controller;

import com.cloudd.commons.auth.annotation.RequiresPermissions;
import com.cloudd.commons.auth.controller.AuthController;
import com.clouddo.commons.common.util.message.Result;
import com.clouddo.log.common.annotation.Log;
import com.clouddo.system.model.SysMenu;
import com.clouddo.system.service.SysMenuService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller层，用于前后台交互、前后台数据格式转换 
 * @author testCodeGenerator
 */
@Controller
@RequestMapping(value = "/sys/menu")
@Api(description = "系统菜单表控制类")
public class SysMenuController extends AuthController<SysMenu> {
	
	@Resource
	private SysMenuService sysMenuService;


    /**
     * 查询数据
     * @param parameterSet 参数
     * @return 数据
     * @throws Exception
     */
   	@ApiOperation(value = "查询系统菜单表API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parameterSet", value = "多参数（内含排序字段、搜索字段，具体详见jqgrid说明文档）", required = false, dataType = "Map", paramType = "query"),
            @ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestBody Map<String, Object> parameterSet) throws Exception {
   	    try {
   	        Page page = this.paging(parameterSet);
            List<SysMenu> data = this.sysMenuService.list(parameterSet);
            if (page != null) {
                Map<String, Object> returnData = new HashMap<String, Object>();
                returnData.put(ROWS, data);
                returnData.put(TOTAL, page.getTotal());
                return Result.success(returnData);
            }
            return Result.success(data);
        } catch (Exception e) {
   	        e.printStackTrace();
   	        return Result.failure(e.getMessage());
        }
    }

    /**
     * 删除操作
     * @param deleteObjects 要删除的实体对象集合，内含对象主键信息
     * @return 结果
     */
    @ApiOperation(value = "删除API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deleteObjects", value = "要删除实体的集合（内含实体主键）", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/delete")
    @ResponseBody
        public Object delete(@RequestBody List<SysMenu> deleteObjects) {
        int deleteNum = this.sysMenuService.delete(deleteObjects);
        return Result.success(deleteNum);
    }

    /**
     * 插入操作
     * @param  sysMenu 要保存的实体
     * @return
     */
    @ApiOperation(value = "插入操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysMenu", value = "要插入数据库的实体类信息", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/insert")
    @ResponseBody
    public Object insert(@RequestBody SysMenu sysMenu) {
        Map<String, Object> saveMeg = this.sysMenuService.saveOrUpdate(sysMenu);
        return Result.success(saveMeg);
    }

    /**
     * 更新操作
     * @param  sysMenu 要更新的实体类
     * @return 更新结果
     */
    @ApiOperation(value = "更新操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysMenu", value = "要更新到数据库的实体类信息", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/update")
    @ResponseBody
    public Object update(@RequestBody SysMenu sysMenu) {
        Map<String, Object> updateMeg = this.sysMenuService.saveOrUpdate(sysMenu);
        return Result.success(updateMeg);
    }

    /**
     * 查询单个结果
     * @param  sysMenu 内含要获取实体的ID信息
     * @return 实体信息
     */
    @ApiOperation(value = "获取单个实体类操作", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysMenu", value = "实体类（内含要获取实体类的主键信息）", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/get")
    @ResponseBody
    public Object get(@RequestBody SysMenu sysMenu) {
        return Result.success(this.sysMenuService.get(sysMenu));
    }

    /**
     * 更新/插入操作
     * @param  sysMenu 要更新/插入的实体类
     * @return 结果
     */
    @ApiOperation(value = "更新/插入操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysMenu", value = "要更新/插入到数据库的实体类信息", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(@RequestBody SysMenu sysMenu) {
        return Result.success(this.sysMenuService.saveOrUpdate(sysMenu));
    }

    @RequestMapping("/saveUpdateTopMenu")
    @ResponseBody
    public Object saveUpdateTopMenu(@RequestBody SysMenu sysMenu) {
        try {
            return Result.success(this.sysMenuService.saveUpdateTopMenu(sysMenu));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.getMessage());
        }
    }

    /**
     * 查询数据和全部节点
     * @param parameters
     * @return
     */
    @RequestMapping("/listWithAllChildren")
    @ResponseBody
    public Object listWithAllChildren(@RequestBody Map<String, Object> parameters) {
        try {
            return Result.success(this.sysMenuService.listWithAllChildren(parameters));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.getMessage());
        }
    }

    /**
     * 删除菜单和菜单下级
     * 需要删除权限
     * @param deleteObjects
     * @return
     */
    @RequiresPermissions("sys:menu:delete")
    @Log("删除菜单和所有下级菜单")
    @RequestMapping("/deleteWithChildren")
    @ResponseBody
    public Object deleteWithChildren (@RequestBody List<SysMenu> deleteObjects) {
        try {
            return Result.success(this.sysMenuService.deleteWithChildren(deleteObjects));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.getMessage());
        }
    }

}