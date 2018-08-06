package com.clouddo.news.server.controller;


import com.cloudd.commons.auth.annotation.RequiresPermissions;
import com.cloudd.commons.auth.controller.AuthController;
import com.clouddo.commons.common.util.message.Result;
import com.clouddo.news.server.dto.CloudColumnDTO;
import com.clouddo.news.server.model.CloudColumn;
import com.clouddo.news.server.service.CloudColumnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller层，用于前后台交互、前后台数据格式转换 
 * @author testCodeGenerator
 */
@Controller
@RequestMapping(value = "/cloudColumn")
@Api(description = "栏目信息控制类")
public class CloudColumnController extends AuthController {
	
	@Resource
	private CloudColumnService cloudColumnService;


    /**
     * 查询数据
     * @param parameterSet 参数
     * @return 数据
     * @throws Exception
     */
   	@ApiOperation(value = "查询栏目信息API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parameterSet", value = "多参数（内含排序字段、搜索字段，具体详见jqgrid说明文档）", required = false, dataType = "Map", paramType = "query"),
            @ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/list")
    @ResponseBody
    @RequiresPermissions("news:cloudColumn:list")
    public Result list(@RequestBody Map<String, Object> parameterSet) throws Exception {
        try {
            Map<String, Object> returnData = new HashMap<String, Object>();
            List<CloudColumnDTO> cloudColumnList = this.cloudColumnService.list(parameterSet);
            returnData.put("cloudColumnList", cloudColumnList);
            return Result.success(returnData);
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
    @DeleteMapping
    @ResponseBody
        public Object delete(@RequestBody List<CloudColumn> deleteObjects) {
        int deleteNum = this.cloudColumnService.delete(deleteObjects);
        return Result.success(deleteNum);
    }

    /**
     * 插入操作
     * @param  cloudColumn 要保存的实体
     * @return
     */
    @ApiOperation(value = "插入操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cloudColumn", value = "要插入数据库的实体类信息", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @PutMapping
    @ResponseBody
    public Object insert(@RequestBody CloudColumn cloudColumn) {
        Map<String, Object> saveMeg = this.cloudColumnService.saveOrUpdate(cloudColumn);
        return Result.success(saveMeg);
    }

    /**
     * 更新操作
     * @param  cloudColumn 要更新的实体类
     * @return 更新结果
     */
    @ApiOperation(value = "更新操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cloudColumn", value = "要更新到数据库的实体类信息", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping
    @ResponseBody
    public Object update(@RequestBody CloudColumn cloudColumn) {
        Map<String, Object> updateMeg = this.cloudColumnService.saveOrUpdate(cloudColumn);
        return Result.success(updateMeg);
    }

    /**
     * 查询单个结果
     * @param  cloudColumn 内含要获取实体的ID信息
     * @return 实体信息
     */
    @ApiOperation(value = "获取单个实体类操作", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cloudColumn", value = "实体类（内含要获取实体类的主键信息）", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/get")
    @ResponseBody
    public Object get(@RequestBody CloudColumn cloudColumn) {
        return Result.success(this.cloudColumnService.get(cloudColumn));
    }

    /**
     * 更新/插入操作
     * @param  cloudColumn 要更新/插入的实体类
     * @return 结果
     */
    @ApiOperation(value = "更新/插入操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cloudColumn", value = "要更新/插入到数据库的实体类信息", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(@RequestBody CloudColumn cloudColumn) {
        return Result.success(this.cloudColumnService.saveOrUpdate(cloudColumn));
    }

}