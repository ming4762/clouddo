package com.clouddo.system.controller;


import com.cloudd.commons.auth.annotation.RequiresPermissions;
import com.cloudd.commons.auth.controller.AuthController;
import com.cloudd.commons.auth.model.User;
import com.clouddo.commons.common.util.message.Result;
import com.clouddo.log.common.annotation.Log;
import com.clouddo.system.service.SysUserService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller层，用于前后台交互、前后台数据格式转换 
 * @author testCodeGenerator
 */
@RestController
@RequestMapping(value = "/sys/user")
@Api(description = "系统用户表控制类")
public class SysUserController extends AuthController<User> {
	
	@Resource
	private SysUserService sysUserService;


    /**
     * 查询数据
     * @param parameterSet 参数
     * @return 数据
     * @throws Exception
     */
   	@ApiOperation(value = "查询系统用户表API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parameterSet", value = "多参数（内含排序字段、搜索字段，具体详见jqgrid说明文档）", required = false, dataType = "Map", paramType = "query"),
            @ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestBody Map<String, Object> parameterSet) throws Exception {
   	    try {
   	        Page page = this.paging(parameterSet);
            List<User> data = this.sysUserService.list(parameterSet);
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
        public Object delete(@RequestBody List<User> deleteObjects) {
        int deleteNum = this.sysUserService.delete(deleteObjects);
        return Result.success(deleteNum);
    }

    /**
     * 插入操作
     * @param  sysUser 要保存的实体
     * @return
     */
    @ApiOperation(value = "插入操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUser", value = "要插入数据库的实体类信息", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/insert")
    @ResponseBody
    public Object insert(@RequestBody User sysUser) {
        Map<String, Object> saveMeg = this.sysUserService.saveOrUpdate(sysUser);
        return Result.success(saveMeg);
    }

    /**
     * 更新操作
     * @param  sysUser 要更新的实体类
     * @return 更新结果
     */
    @ApiOperation(value = "更新操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUser", value = "要更新到数据库的实体类信息", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/update")
    @ResponseBody
    public Object update(@RequestBody User sysUser) {
        Map<String, Object> updateMeg = this.sysUserService.saveOrUpdate(sysUser);
        return Result.success(updateMeg);
    }

    /**
     * 查询单个结果
     * 需要查询权限
     * @param  sysUser 内含要获取实体的ID信息
     * @return 实体信息
     */
    @ApiOperation(value = "获取单个实体类操作", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUser", value = "实体类（内含要获取实体类的主键信息）", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/get")
    @RequiresPermissions("sys:user:query")
    public Object get(@RequestBody User sysUser) {
        try {
            return Result.success(this.sysUserService.get(sysUser));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.getMessage());
        }
    }

    /**
     * 查询用户信息和用户权限
     * @param sysUser
     * @return
     */
    @RequestMapping("/getWithPermissions")
    @RequiresPermissions("sys:user:permission")
    @Log("查询用户权限信息")
    public Object getWithPermissions(@RequestBody User sysUser) {
        try {
            // 查询用户
            sysUser = this.sysUserService.get(sysUser);
            // 查询权限
            List<String> permissionList = this.sysUserService.queryPermissions(sysUser);
            Map<String, Object> returnData = new HashMap<String, Object>();
            returnData.put("user", sysUser);
            returnData.put("permissionList", permissionList);
            return Result.success(returnData);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.getMessage());
        }
    }

    /**
     * 更新/插入操作
     * @param  sysUser 要更新/插入的实体类
     * @return 结果
     */
    @ApiOperation(value = "更新/插入操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUser", value = "要更新/插入到数据库的实体类信息", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(@RequestBody User sysUser) {
        return Result.success(this.sysUserService.saveOrUpdate(sysUser));
    }

    /**
     * 验证用户是否登录
     * @param parameters
     * @return
     */
    @PostMapping("/validate")
    @Log("登录验证")
    public Object validate(@RequestBody Map<String, Object> parameters) {
        if(StringUtils.isEmpty(parameters.get("username"))) {
            return null;
        }
        if(StringUtils.isEmpty(parameters.get("password"))) {
            return null;
        }
        List<User> users = this.sysUserService.list(parameters);
        if(users.size() == 1) {
            return Result.success(users.get(0));
        }
        return null;
    }

}