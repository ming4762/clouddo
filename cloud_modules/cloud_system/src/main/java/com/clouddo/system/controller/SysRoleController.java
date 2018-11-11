package com.clouddo.system.controller;

import com.cloudd.commons.auth.annotation.RequiresPermissions;
import com.cloudd.commons.auth.controller.AuthController;
import com.clouddo.commons.common.util.message.Result;
import com.clouddo.system.model.SysRole;
import com.clouddo.system.service.SysRoleService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller层，用于前后台交互、前后台数据格式转换 
 * @author testCodeGenerator
 */
@RestController
@RequestMapping(value = "/sys/role")
@Api(description = "系统角色表控制类")
public class SysRoleController extends AuthController<SysRole> {
	
	@Resource
	private SysRoleService sysRoleService;


    /**
     * 查询数据
     * @param parameterSet 参数
     * @return 数据
     * @throws Exception
     */
   	@ApiOperation(value = "查询系统角色表API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parameterSet", value = "多参数（内含排序字段、搜索字段，具体详见jqgrid说明文档）", required = false, dataType = "Map", paramType = "query"),
            @ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequiresPermissions("sys:role:query")
    @RequestMapping("/list")
    public Object list(@RequestBody Map<String, Object> parameterSet) throws Exception {
   	    try {
   	        Page page = this.paging(parameterSet);
            List<SysRole> data = this.sysRoleService.list(parameterSet);
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
        public Object delete(@RequestBody List<SysRole> deleteObjects) {
        int deleteNum = this.sysRoleService.delete(deleteObjects);
        return Result.success(deleteNum);
    }

    /**
     * 插入操作
     * @param  sysRole 要保存的实体
     * @return
     */
    @ApiOperation(value = "插入操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRole", value = "要插入数据库的实体类信息", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/insert")
    @ResponseBody
    public Object insert(@RequestBody SysRole sysRole) {
        Map<String, Object> saveMeg = this.sysRoleService.saveOrUpdate(sysRole);
        return Result.success(saveMeg);
    }

    /**
     * 更新操作
     * @param  sysRole 要更新的实体类
     * @return 更新结果
     */
    @ApiOperation(value = "更新操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRole", value = "要更新到数据库的实体类信息", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/update")
    @ResponseBody
    public Object update(@RequestBody SysRole sysRole) {
        Map<String, Object> updateMeg = this.sysRoleService.saveOrUpdate(sysRole);
        return Result.success(updateMeg);
    }

    /**
     * 查询单个结果
     * @param  sysRole 内含要获取实体的ID信息
     * @return 实体信息
     */
    @ApiOperation(value = "获取单个实体类操作", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRole", value = "实体类（内含要获取实体类的主键信息）", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/get")
    @ResponseBody
    public Object get(@RequestBody SysRole sysRole) {
        return Result.success(this.sysRoleService.get(sysRole));
    }

    /**
     * 更新/插入操作
     * @param  sysRole 要更新/插入的实体类
     * @return 结果
     */
    @ApiOperation(value = "更新/插入操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRole", value = "要更新/插入到数据库的实体类信息", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequiresPermissions({"sys:role:add", "sys:role:update"})
    @RequestMapping("/saveOrUpdate")
    public Object saveOrUpdate(@RequestBody SysRole sysRole) {
        try {
            return Result.success(this.sysRoleService.saveOrUpdate(sysRole));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.getMessage());
        }
    }

    /**
     * 查询角色对应人员
     * @param roleList 角色列表
     * TODO 未严格测试多个对象
     * @return
     */
    @RequiresPermissions("sys:role:query")
    @RequestMapping("/queryUser")
    public Object queryUser(@RequestBody List<SysRole> roleList) {
        try {
            roleList = this.sysRoleService.queryUser(roleList);
            return Result.success(roleList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.getMessage());
        }
    }

    /**
     * 设置角色用户
     * @param parameters
     * @return
     */
    @RequiresPermissions("sys:role:setUser")
    @RequestMapping("/setUser")
    public Object setUser(@RequestBody Map<String, Object> parameters) {
        try {
            String roleId = (String) parameters.get("roleId");
            List<String> userIdList = (List<String>) parameters.get("userIdList");
            Map<String, List<String>> parameterSet = new HashMap<String, List<String>>();
            parameterSet.put(roleId, userIdList);
            this.sysRoleService.setUser(parameterSet);
            return Result.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.getMessage());
        }
    }

}