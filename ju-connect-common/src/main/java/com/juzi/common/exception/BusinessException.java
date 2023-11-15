package com.juzi.common.exception;


import com.juzi.common.biz.StatusCode;
import lombok.Getter;

/**
 * 自定义业务异常类
 *
 * @author codejuzi
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 状态码
     */
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(StatusCode statusCode) {
        this(statusCode.getCode(), statusCode.getMessage());
    }

    public BusinessException(StatusCode statusCode, String message) {
        this(statusCode.getCode(), message);
    }

}
