package com.clouddo.news.server.controller;


import com.cloudd.commons.auth.annotation.RequiresPermissions;
import com.cloudd.commons.auth.controller.AuthController;
import com.clouddo.commons.common.util.message.Result;
import com.clouddo.log.common.annotation.Log;
import com.clouddo.news.server.dto.CloudNewsDTO;
import com.clouddo.news.server.model.CloudNews;
import com.clouddo.news.server.service.CloudNewsService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Controller层，用于前后台交互、前后台数据格式转换 
 * @author testCodeGenerator
 */
@Controller
@RequestMapping(value = "/cloudNews")
@Api(description = "新闻控制类")
public class CloudNewsController extends AuthController<CloudNews> {
	
	@Resource
	private CloudNewsService cloudNewsService;



    /**
     * 查询数据
     * @param parameterSet 参数
     * @return 数据
     * @throws Exception
     */
   	@ApiOperation(value = "查询新闻API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parameterSet", value = "多参数（内含排序字段、搜索字段，具体详见jqgrid说明文档）", required = false, dataType = "Map", paramType = "query"),
            @ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/list")
    @ResponseBody
    @RequiresPermissions("news:cloudNews:list")
    public Result list(@RequestBody Map<String, Object> parameterSet) throws Exception {

   	    try {
            //判断是否分页
            if(parameterSet.get(PAGE_SIZE) != null) {
                Integer limit = (Integer) parameterSet.get(PAGE_SIZE);
                Integer offset = parameterSet.get(OFFSET) == null ? 0 : (Integer) parameterSet.get(OFFSET);
                Page page = PageHelper.offsetPage(offset, limit);
                String order = this.analysisOrder(parameterSet);
                if(!StringUtils.isEmpty(order)) {
                    PageHelper.orderBy(order);
                }
            }
            List<CloudNewsDTO> cloudNewsList = this.cloudNewsService.list(parameterSet);
            return Result.success(cloudNewsList);
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
    @DeleteMapping("/batchDelete")
    @ResponseBody
        public Object batchDelete(@RequestBody List<CloudNews> deleteObjects) {
        int deleteNum = this.cloudNewsService.delete(deleteObjects);
        return Result.success(deleteNum);
    }

    /**
     * 删除操作
     * @param id 要删除的实体对象集合，内含对象主键信息
     * @return 结果
     */
    @ApiOperation(value = "删除API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deleteObjects", value = "要删除实体的集合（内含实体主键）", required = true, dataType = "Object", paramType = "query"),
            @ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @DeleteMapping("/{id}")
    @ResponseBody
    public Object delete(@PathVariable String id) {
        int deleteNum = this.cloudNewsService.delete(id);
        return Result.success(deleteNum);
    }

    /**
     * 插入操作
     * @param  cloudNews 要保存的实体
     * @return
     */
    @ApiOperation(value = "插入操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cloudNews", value = "要插入数据库的实体类信息", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/insert")
    @ResponseBody
    @Log("发布新闻")
    public Object insert(@RequestBody CloudNews cloudNews) {
        //设置创建人、创建时间
        try {
            cloudNews.setAuthorId(this.getUserId());
            cloudNews.setCreateTime(new Date());
            Map<String, Object> saveMeg = this.cloudNewsService.saveOrUpdate(cloudNews);
            return Result.success(saveMeg);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.getMessage());
        }
    }

    /**
     * 更新操作
     * @param  cloudNews 要更新的实体类
     * @return 更新结果
     */
    @ApiOperation(value = "更新操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cloudNews", value = "要更新到数据库的实体类信息", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping
    @ResponseBody
    public Object update(@RequestBody CloudNews cloudNews) {
        Map<String, Object> updateMeg = this.cloudNewsService.saveOrUpdate(cloudNews);
        return Result.success(updateMeg);
    }

    /**
     * 查询单个结果
     * @param  id 内含要获取实体的ID信息
     * @return 实体信息
     */
    @ApiOperation(value = "获取单个实体类操作", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cloudNews", value = "实体类（内含要获取实体类的主键信息）", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/get/{id}")
    @ResponseBody
//    @Log("查询新闻详情")
    @RequiresPermissions("news:cloudNews:get")
    public Object get(@PathVariable String id) {
       try {
           CloudNews cloudNews = new CloudNews();
           cloudNews.setNewsId(id);
           return Result.success(this.cloudNewsService.get(cloudNews));
       } catch (Exception e) {
           e.printStackTrace();
           return Result.failure(e.getMessage());
       }
    }

    /**
     * 更新/插入操作
     * @param  cloudNews 要更新/插入的实体类
     * @return 结果
     */
    @ApiOperation(value = "更新/插入操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cloudNews", value = "要更新/插入到数据库的实体类信息", required = true, dataType = "Object", paramType = "query"),
    			@ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(@RequestBody CloudNews cloudNews) {
        return Result.success(this.cloudNewsService.saveOrUpdate(cloudNews));
    }

}