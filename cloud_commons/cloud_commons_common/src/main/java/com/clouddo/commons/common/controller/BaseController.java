package com.clouddo.commons.common.controller;

import org.springframework.util.StringUtils;

import javax.persistence.Column;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 * @author zhongming
 * @since 3.0
 * 2018/5/29下午2:30
 */
public class BaseController<T> {

    /**
     * 分页页数
     */
    public static final String PAGE_SIZE = "limit";

    /**
     * 起始记录数
     */
    public static final String OFFSET = "offset";

    /**
     * 排序字段
     */
    public static final String SORT_NAME = "sortName";

    public static final String SORT_ORDER = "sortOrder";

    /**
     * 解析排序
     * @param parameters
     */
    protected String analysisOrder(Map<String, Object> parameters) throws NoSuchFieldException {

        //获取排序列
        String sortName = (String) parameters.get(SORT_NAME);
        if(!StringUtils.isEmpty(sortName)) {
            //以逗号分隔，获取排序列列表
            String[] sortNameList = sortName.split(",");
            //获取排序防线
            String sortOrder = (String) parameters.get(SORT_ORDER);
            String[] sortOrderList = {};
            if(!StringUtils.isEmpty(sortOrder)) {
                sortOrderList = sortOrder.split(",");
            }

            //排序信息
            StringBuffer orderMessage = new StringBuffer();
            //获取泛型的实际类型，泛型为传入的实体类
            Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            //遍历获取排序信息
            for(int i=0; i<sortNameList.length; i++) {
                //获取字段对应的数据库字段
                String dbFiled = tClass.getDeclaredField(sortNameList[i]).getAnnotation(Column.class).name();
                orderMessage.append(dbFiled)
                        .append(" ")
                        .append(sortOrderList[i] == null ? "asc" : sortOrderList[i])
                        .append(",");
            }
            //去除最后一个逗号
            if(orderMessage.length() > 0) {
                orderMessage.deleteCharAt(orderMessage.length() - 1);
            }
            return orderMessage.toString();
        }
        return null;
    }

}
