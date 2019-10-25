package com.clouddo.commons.common.controller

import com.github.pagehelper.Page
import com.github.pagehelper.PageHelper
import org.springframework.util.StringUtils
import java.lang.reflect.ParameterizedType
import javax.persistence.Column

/**
 * @author zhongming
 * @since 3.0
 * 2018/5/29下午2:30
 */
open class BaseController<T> {

    /**
     * 解析排序
     * @param parameters
     */
    @Throws(NoSuchFieldException::class)
    protected fun analysisOrder(parameters: Map<String, Any>): String? {

        //获取排序列
        val sortName = parameters[SORT_NAME] as String
        if (!StringUtils.isEmpty(sortName)) {
            //以逗号分隔，获取排序列列表
            val sortNameList = sortName.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            //获取排序防线
            val sortOrder = parameters[SORT_ORDER] as String
            var sortOrderList = arrayOf<String>()
            if (!StringUtils.isEmpty(sortOrder)) {
                sortOrderList = sortOrder.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            }

            //排序信息
            val orderMessage = StringBuffer()
            //获取泛型的实际类型，泛型为传入的实体类
            val tClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
            //遍历获取排序信息
            for (i in sortNameList.indices) {
                //获取字段对应的数据库字段
                val dbFiled = tClass.getDeclaredField(sortNameList[i]).getAnnotation(Column::class.java).name
                orderMessage.append(dbFiled)
                        .append(" ")
                        .append(if (sortOrderList[i] == null) "asc" else sortOrderList[i])
                        .append(",")
            }
            //去除最后一个逗号
            if (orderMessage.length > 0) {
                orderMessage.deleteCharAt(orderMessage.length - 1)
            }
            return orderMessage.toString()
        }
        return null
    }

    /**
     * 进行分页
     * @param parameterSet
     * @return
     * @throws NoSuchFieldException
     */
    @Throws(NoSuchFieldException::class)
    protected fun paging(parameterSet: Map<String, Any>): Page<*>? {
        var page: Page<*>? = null
        if (parameterSet[PAGE_SIZE] != null) {
            val limit = parameterSet[PAGE_SIZE] as Int
            val offset = if (parameterSet[OFFSET] == null) 0 else parameterSet[OFFSET] as Int
            val order = this.analysisOrder(parameterSet)
            if (!StringUtils.isEmpty(order)) {
                PageHelper.orderBy(order)
            }
            page = PageHelper.offsetPage<Any>(offset, limit)
        }
        return page
    }

    companion object {

        /**
         * 分页页数
         */
        const val PAGE_SIZE = "limit"

        /**
         * 起始记录数
         */
        const val OFFSET = "offset"

        const val ROWS = "rows"
        const val TOTAL = "total"

        /**
         * 排序字段
         */
        const val SORT_NAME = "sortName"

        const val SORT_ORDER = "sortOrder"
    }

}
