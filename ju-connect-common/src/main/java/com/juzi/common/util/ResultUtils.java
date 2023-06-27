package com.juzi.common.util;


import com.juzi.common.biz.BaseResponse;
import com.juzi.common.biz.StatusCode;

/**
 * 返回结果生成工具类
 *
 * @author codejuzi
 */
public class ResultUtils {

    /**
     * 成功响应的结果
     *
     * @param data 成功响应的数据
     * @param <T>  数据的类型
     * @return 通用返回类
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(StatusCode.SUCCESS.getCode(), data, StatusCode.SUCCESS.getMessage());
    }

    /**
     * 成功响应的结果
     *
     * @param data    成功响应的数据
     * @param message 响应信息
     * @param <T>     数据的类型
     * @return 通用返回类
     */

    public static <T> BaseResponse<T> success(T data, String message) {
        return new BaseResponse<>(StatusCode.SUCCESS.getCode(), data, message);
    }

    /**
     * 响应失败的结果
     *
     * @param statusCode 响应的状态码
     * @return 通用返回类
     */
    public static BaseResponse<?> error(StatusCode statusCode) {
        return new BaseResponse<>(statusCode.getCode(), null, statusCode.getMessage());
    }

    /**
     * 响应失败的结果
     *
     * @param code    状态码
     * @param message 响应失败的信息
     * @return 通用返回类
     */
    public static BaseResponse<?> error(int code, String message) {
        return new BaseResponse<>(code, null, message);
    }
}
