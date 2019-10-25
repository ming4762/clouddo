package com.clouddo.system.controller


import com.clouddo.auth.common.annotation.RequiresPermissions
import com.clouddo.auth.common.model.User
import com.clouddo.commons.common.controller.BaseController
import com.clouddo.commons.common.util.message.Result
import com.clouddo.log.common.annotation.Log
import com.clouddo.system.service.SysUserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.http.MediaType
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.annotation.Resource

/**
 * Controller层，用于前后台交互、前后台数据格式转换
 * @author testCodeGenerator
 */
@RestController
@RequestMapping( "/sys/user")
@Api(description = "系统用户表控制类")
class SysUserController : BaseController<User>() {

    @Resource
    private lateinit var sysUserService: SysUserService


    /**
     * 查询数据
     * @param parameterSet 参数
     * @return 数据
     * @throws Exception
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:user:query")
    fun list(@RequestBody parameterSet: Map<String, Any>): Any {
        try {
            val page = this.paging(parameterSet)
            val data = this.sysUserService.list(parameterSet)
            if (page != null) {
                val returnData = HashMap<String, Any>()
                returnData[ROWS] = data
                returnData[TOTAL] = page.getTotal()
                return Result.success<Map<String, Any>>(returnData)
            }
            return Result.success(data)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure<Any>(e.message)
        }

    }

    /**
     * 删除操作
     * @param deleteObjects 要删除的实体对象集合，内含对象主键信息
     * @return 结果
     */
    @ApiOperation(value = "删除API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams(ApiImplicitParam(name = "deleteObjects", value = "要删除实体的集合（内含实体主键）", required = true, dataType = "Object", paramType = "query"), ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header"))
    @RequestMapping("/delete")
    fun delete(@RequestBody deleteObjects: List<User>): Any {
        val deleteNum = this.sysUserService.delete(deleteObjects)
        return Result.success(deleteNum)
    }

    /**
     * 插入操作
     * @param  sysUser 要保存的实体
     * @return
     */
    @ApiOperation(value = "插入操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams(ApiImplicitParam(name = "sysUser", value = "要插入数据库的实体类信息", required = true, dataType = "Object", paramType = "query"), ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header"))
    @RequestMapping("/insert")
    @RequiresPermissions("sys:user:update")
    fun insert(@RequestBody sysUser: User): Any {
        val saveMeg = this.sysUserService.saveOrUpdate(sysUser)
        return Result.success(saveMeg)
    }

    /**
     * 更新操作
     * @param  sysUser 要更新的实体类
     * @return 更新结果
     */
    @ApiOperation(value = "更新操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams(ApiImplicitParam(name = "sysUser", value = "要更新到数据库的实体类信息", required = true, dataType = "Object", paramType = "query"), ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header"))
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    fun update(@RequestBody sysUser: User): Any {
        val updateMeg = this.sysUserService.saveOrUpdate(sysUser)
        return Result.success(updateMeg)
    }

    /**
     * 查询单个结果
     * 需要查询权限
     * @param  sysUser 内含要获取实体的ID信息
     * @return 实体信息
     */
    @ApiOperation(value = "获取单个实体类操作", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams(ApiImplicitParam(name = "sysUser", value = "实体类（内含要获取实体类的主键信息）", required = true, dataType = "Object", paramType = "query"), ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header"))
    @RequestMapping("/get")
    @RequiresPermissions("sys:user:query")
    operator fun get(@RequestBody sysUser: User): Any {
        try {
            return Result.success(this.sysUserService.get(sysUser))
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure<Any>(e.message)
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
    fun getWithPermissions(@RequestBody sysUser: User): Any {
        try {
            // 查询用户
            val user = this.sysUserService.get(sysUser)
            // 查询权限
            val permissionList = this.sysUserService.queryPermissions(user)
            val returnData = HashMap<String, Any>()
            returnData["user"] = user
            returnData["permissionList"] = permissionList
            return Result.success<Map<String, Any>>(returnData)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure<Any>(e.message)
        }

    }

    /**
     * 更新/插入操作
     * @param  sysUser 要更新/插入的实体类
     * @return 结果
     */
    @ApiOperation(value = "更新/插入操作API", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams(ApiImplicitParam(name = "sysUser", value = "要更新/插入到数据库的实体类信息", required = true, dataType = "Object", paramType = "query"), ApiImplicitParam(name = "authority", value = "header里的认证串信息", required = true, dataType = "string", paramType = "header"))
    @RequestMapping("/saveUpdate")
    @RequiresPermissions("sys:user:update")
    fun saveUpdate(@RequestBody sysUser: User): Any {
        return Result.success(this.sysUserService.saveOrUpdate(sysUser))
    }

    /**
     * 验证用户是否登录
     * @param parameters
     * @return
     */
    @PostMapping("/validate")
    @Log("登录验证")
    fun validate(@RequestBody parameters: Map<String, Any>): Result<User> {
        try {
            if (StringUtils.isEmpty(parameters["username"]) || StringUtils.isEmpty(parameters["password"])) {
                return Result.success(null)
            }
            val users = this.sysUserService.list(parameters)
            return if (users.size == 1) {
                Result.success(users[0])
            } else Result.success(null)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(null)
        }
    }

}