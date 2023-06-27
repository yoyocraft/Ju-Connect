package com.juzi.common.biz;

import lombok.Data;

/**
 * 通用返回类
 *
 * @author codejuzi
 */
@Data
public class BaseResponse<T> {

    /**
     * 状态码
     */
    private int code;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 返回信息
     */
    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
}
