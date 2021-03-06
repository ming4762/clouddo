package com.clouddo.system.controller;


import com.cloudd.commons.auth.annotation.RequiresPermissions;
import com.cloudd.commons.auth.controller.AuthController;
import com.clouddo.commons.common.model.Tree;
import com.clouddo.commons.common.util.message.Result;
import com.clouddo.system.model.SysFunction;
import com.clouddo.system.service.SysFunctionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Controller层，用于前后台交互、前后台数据格式转换 
 * @author testCodeGenerator
 */
@RestController
@RequestMapping(value = "/sys/function")
@Api(description = "系统功能管理控制类")
public class SysFunctionController extends AuthController<SysFunction> {
	
	@Resource
	private SysFunctionService sysFunctionService;


    /**
     * 查询数据
     * @param parameterSet 参数
     * @return 数据
     * @throws Exception
     */
   	@ApiOperation(value = "查询系统功能管理API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parameterSet", value = "多参数（内含排序字段、搜索字段，具体详见jqgrid说明文档）", required = false, dataType = "Map", paramType = "query"),
            @ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestBody Map<String, Object> parameterSet) throws Exception {
   	    try {
   	        String order = this.analysisOrder(parameterSet);
   	        if (!StringUtils.isEmpty(order)) {
   	            parameterSet.put("order", order);
            }
            List<SysFunction> sysFunctionList = this.sysFunctionService.list(parameterSet);
            return Result.success(sysFunctionList);
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
        public Object delete(@RequestBody List<SysFunction> deleteObjects) {
        int deleteNum = this.sysFunctionService.delete(deleteObjects);
        return Result.success(deleteNum);
    }

    /**
     * 插入操作
     * @param  sysFunction 要保存的实体
     * @return
     */
    @ApiOperation(value = "插入操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysFunction", value = "要插入数据库的实体类信息", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/insert")
    @ResponseBody
    public Object insert(@RequestBody SysFunction sysFunction) {
        Map<String, Object> saveMeg = this.sysFunctionService.saveOrUpdate(sysFunction);
        return Result.success(saveMeg);
    }

    /**
     * 更新操作
     * @param  sysFunction 要更新的实体类
     * @return 更新结果
     */
    @ApiOperation(value = "更新操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysFunction", value = "要更新到数据库的实体类信息", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/update")
    @ResponseBody
    public Object update(@RequestBody SysFunction sysFunction) {
        Map<String, Object> updateMeg = this.sysFunctionService.saveOrUpdate(sysFunction);
        return Result.success(updateMeg);
    }

    /**
     * 查询单个结果
     * @param  sysFunction 内含要获取实体的ID信息
     * @return 实体信息
     */
    @ApiOperation(value = "获取单个实体类操作", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysFunction", value = "实体类（内含要获取实体类的主键信息）", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/get")
    @ResponseBody
    public Object get(@RequestBody SysFunction sysFunction) {
        return Result.success(this.sysFunctionService.get(sysFunction));
    }

    /**
     * 更新/插入操作
     * @param  sysFunction 要更新/插入的实体类
     * @return 结果
     */
    @ApiOperation(value = "更新/插入操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysFunction", value = "要更新/插入到数据库的实体类信息", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(@RequestBody SysFunction sysFunction) {
        try {
            return Result.success(this.sysFunctionService.saveOrUpdate(sysFunction));
        } catch (Exception e) {
            e.printStackTrace();
            return  Result.failure(e.getMessage());
        }

    }

    /**
     * 查询树形结构
     * @param parameters
     * @return
     */
    @RequiresPermissions("sys:function:query")
    @RequestMapping("/tree")
    public Object tree(@RequestBody Map<String, Object> parameters) {
        try {
            List<Tree<SysFunction>> treeList = this.sysFunctionService.treeList(parameters);
            return Result.success(treeList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.getMessage());
        }
    }
}