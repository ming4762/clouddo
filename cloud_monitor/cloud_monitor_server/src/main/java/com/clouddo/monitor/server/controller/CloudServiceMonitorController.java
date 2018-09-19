package com.clouddo.monitor.server.controller;


import com.cloudd.commons.auth.controller.AuthController;
import com.clouddo.commons.common.util.message.Result;
import com.clouddo.monitor.server.model.CloudServiceMonitor;
import com.clouddo.monitor.server.service.CloudServiceMonitorService;
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
@RequestMapping(value = "/cloudServiceMonitor")
@Api(description = "服务监控控制类")
public class CloudServiceMonitorController extends AuthController<CloudServiceMonitor> {
	
	@Resource
	private CloudServiceMonitorService cloudServiceMonitorService;


    /**
     * 查询数据
     * @param parameterSet 参数
     * @return 数据
     * @throws Exception
     */
   	@ApiOperation(value = "查询服务监控API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parameterSet", value = "多参数（内含排序字段、搜索字段，具体详见jqgrid说明文档）", required = false, dataType = "Map", paramType = "query"),
            @ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestBody Map<String, Object> parameterSet) throws Exception {
   	    try {
   	        Page page = this.paging(parameterSet);
            List<CloudServiceMonitor> data = this.cloudServiceMonitorService.list(parameterSet);
            if (page != null) {
                Map<String, Object> returnData = new HashMap<String, Object>();
                returnData.put("rows", data);
                returnData.put("total", page.getTotal());
                return Result.success(returnData);
            }
            return Result.success(data);
        } catch (Exception e) {
   	        e.printStackTrace();
   	        return Result.failure("获取服务监控信息失败", e.getMessage());
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
    public Result delete(@RequestBody List<CloudServiceMonitor> deleteObjects) {
        try {
            return Result.success(this.cloudServiceMonitorService.delete(deleteObjects));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure("删除失败", e.getMessage());
        }
    }

    /**
     * 插入操作
     * @param  cloudServiceMonitor 要保存的实体
     * @return
     */
    @ApiOperation(value = "插入操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cloudServiceMonitor", value = "要插入数据库的实体类信息", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/insert")
    @ResponseBody
    public Object insert(@RequestBody CloudServiceMonitor cloudServiceMonitor) {
        try {
            Map<String, Object> saveMeg = this.cloudServiceMonitorService.saveOrUpdate(cloudServiceMonitor);
            return Result.success(saveMeg);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure("插入失败", e.getMessage());
        }
    }

    /**
     * 更新操作
     * @param  cloudServiceMonitor 要更新的实体类
     * @return 更新结果
     */
    @ApiOperation(value = "更新操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cloudServiceMonitor", value = "要更新到数据库的实体类信息", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/update")
    @ResponseBody
    public Object update(@RequestBody CloudServiceMonitor cloudServiceMonitor) {
        try {
            Map<String, Object> updateMeg = this.cloudServiceMonitorService.saveOrUpdate(cloudServiceMonitor);
            return Result.success(updateMeg);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure("更新失败", e.getMessage());
        }
    }

    /**
     * 查询单个结果
     * @param  cloudServiceMonitor 内含要获取实体的ID信息
     * @return 实体信息
     */
    @ApiOperation(value = "获取单个实体类操作", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cloudServiceMonitor", value = "实体类（内含要获取实体类的主键信息）", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/get")
    @ResponseBody
    public Object get(@RequestBody CloudServiceMonitor cloudServiceMonitor) {
        return Result.success(this.cloudServiceMonitorService.get(cloudServiceMonitor));
    }

    /**
     * 更新/插入操作
     * @param  cloudServiceMonitor 要更新/插入的实体类
     * @return 结果
     */
    @ApiOperation(value = "更新/插入操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cloudServiceMonitor", value = "要更新/插入到数据库的实体类信息", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(@RequestBody CloudServiceMonitor cloudServiceMonitor) {
        try {
            return Result.success(this.cloudServiceMonitorService.saveOrUpdate(cloudServiceMonitor));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure("插入/更新失败", e.getMessage());
        }
    }

}