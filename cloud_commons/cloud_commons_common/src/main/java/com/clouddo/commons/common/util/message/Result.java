package com.clouddo.commons.common.util.message;

import java.lang.reflect.Field;

/**
 * 结果封装
 * @author zhongming
 * @since 3.0
 * 2018/5/4下午3:11
 */
public class Result<T> {

    private Integer code;

    private String message;

    private T data;

    private Boolean ok = true;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private static <T> Result<T> newInstance() {
        return new Result<>();
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = newInstance();
        result.setData(data);
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMessage(ResultCodeEnum.SUCCESS.getMsg());

        if (data == null) {
            result.setData(null);
        } else {
            // 遇到没有属性的空类,防止JSON转换的时候异常

            Field[] fields = data.getClass().getDeclaredFields();
            if (fields.length == 0) {
                result.setData(null);
            }
        }
        return result;
    }

    public static <T> Result<T> failure(String message) {
        Result<T> result = newInstance();
        result.setCode(ResultCodeEnum.FAILURE.getCode());
        result.setMessage(message);
        result.ok = false;
        return result;
    }

    public static <T> Result<T> failure(Integer errorCode, String message, T data) {
        Result<T> result = newInstance();
        result.setCode(errorCode);
        result.setMessage(message);
        result.setData(data);

        if (data == null) {
            result.setData(null);
        }
        result.ok = false;
        return result;
    }

    public static <T> Result<T> failure(Integer errorCode, String message) {
        Result<T> result = newInstance();
        result.setCode(errorCode);
        result.setMessage(message);
        result.setData(null);
        result.ok = false;
        return result;
    }

    /**
     * 获取失败对象
     * @author ming
     * @param message 返回信息
     * @param data 返回数据
     * @return 失败对象
     */
    public static <T> Result<T> failure(String message, T data) {
        return failure(ResultCodeEnum.FAILURE.getCode(), message, data);
    }

    public static <T> Result<T> failure(T a) {
        Result<T> result = newInstance();
        result.setCode(ResultCodeEnum.FAILURE.getCode());
        result.setData(a);
        return result;
    }
}
