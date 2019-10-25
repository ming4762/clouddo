package com.clouddo.quartz.common.controller;


import com.cloudd.commons.auth.annotation.RequiresPermissions;
import com.cloudd.commons.auth.controller.AuthController;
import com.clouddo.commons.common.util.message.Result;
import com.clouddo.log.common.annotation.Log;
import com.clouddo.quartz.common.model.CloudTimedTask;
import com.clouddo.quartz.common.service.CloudTimedTaskService;
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
@RequestMapping(value = "/cloudTimedTask")
@Api(description = "定时任务类控制类")
public class CloudTimedTaskController extends AuthController<CloudTimedTask> {
	
	@Resource
	private CloudTimedTaskService cloudTimedTaskService;


    /**
     * 查询数据
     * @param parameterSet 参数
     * @return 数据
     * @throws Exception
     */
   	@ApiOperation(value = "查询定时任务类API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parameterSet", value = "多参数（内含排序字段、搜索字段，具体详见jqgrid说明文档）", required = false, dataType = "Map", paramType = "query"),
            @ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/list")
    @ResponseBody
    public Result list(@RequestBody Map<String, Object> parameterSet) throws Exception {
        try {
            Page page = this.paging(parameterSet);
            List<CloudTimedTask> cloudTimedTaskList = this.cloudTimedTaskService.list(parameterSet);
            if(page != null) {
                Map<String, Object> data = new HashMap<String, Object>();
                data.put(Companion.getROWS(), cloudTimedTaskList);
                data.put(Companion.getTOTAL(), page.getTotal());
                return Result.success(data);
            }
            return Result.success(cloudTimedTaskList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure("获取定时任务列表失败", e.getMessage());
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
    @Log("删除定时任务")
    @RequiresPermissions("quartz:timedTask:delete")
    public Object delete(@RequestBody List<CloudTimedTask> deleteObjects) {
        try {
            int deleteNum = this.cloudTimedTaskService.delete(deleteObjects);
            return Result.success(deleteNum);
        } catch (Exception e) {
            return Result.failure("删除时发生错误", e.getMessage());
        }
    }

    /**
     * 插入操作
     * @param  cloudTimedTask 要保存的实体
     * @return
     */
    @ApiOperation(value = "插入操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cloudTimedTask", value = "要插入数据库的实体类信息", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/insert")
    @ResponseBody
    @Log("插入定时任务")
    @RequiresPermissions("quartz:timedTask:saveOrUpdate")
    public Object insert(@RequestBody CloudTimedTask cloudTimedTask) {
        try {
            Map<String, Object> saveMeg = this.cloudTimedTaskService.saveOrUpdate(cloudTimedTask);
            return Result.success(saveMeg);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure("插入定时任务时发生错误", e.getMessage());
        }
    }

    /**
     * 更新操作
     * @param  cloudTimedTask 要更新的实体类
     * @return 更新结果
     */
    @ApiOperation(value = "更新操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cloudTimedTask", value = "要更新到数据库的实体类信息", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/update")
    @ResponseBody
    @Log("更新定时任务")
    @RequiresPermissions("quartz:timedTask:saveOrUpdate")
    public Object update(@RequestBody CloudTimedTask cloudTimedTask) {
        try {
            Map<String, Object> updateMeg = this.cloudTimedTaskService.saveOrUpdate(cloudTimedTask);
            return Result.success(updateMeg);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure("更新定时任务发生错误", e.getMessage());
        }

    }

    /**
     * 查询单个结果
     * @param  cloudTimedTask 内含要获取实体的ID信息
     * @return 实体信息
     */
    @ApiOperation(value = "获取单个实体类操作", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cloudTimedTask", value = "实体类（内含要获取实体类的主键信息）", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/get")
    @ResponseBody
    public Object get(@RequestBody CloudTimedTask cloudTimedTask) {
        return Result.success(this.cloudTimedTaskService.get(cloudTimedTask));
    }

    /**
     * 更新/插入操作
     * @param  cloudTimedTask 要更新/插入的实体类
     * @return 结果
     */
    @ApiOperation(value = "更新/插入操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cloudTimedTask", value = "要更新/插入到数据库的实体类信息", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    @RequiresPermissions("quartz:timedTask:saveOrUpdate")
    @Log("更新或添加定时任务")
    public Object saveOrUpdate(@RequestBody CloudTimedTask cloudTimedTask) {
        try {
            return Result.success(this.cloudTimedTaskService.saveOrUpdate(cloudTimedTask));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure("更新或添加定时任务出错", e.getMessage());
        }
    }

    /**
     * 启动 关闭任务
     * @param cloudTimedTask
     * @return
     */
    @RequestMapping("/openClose")
    @ResponseBody
    @RequiresPermissions("quartz:timedTask:openClose")
    @Log("关闭/开启定时任务")
    public Result openClose(@RequestBody CloudTimedTask cloudTimedTask) {
        try {
            cloudTimedTask = this.cloudTimedTaskService.openClose(cloudTimedTask);
            return Result.success(cloudTimedTask);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure("启动或关闭定时任务失败", e.getMessage());
        }
    }

}