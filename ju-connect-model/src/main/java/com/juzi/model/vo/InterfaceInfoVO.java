package com.juzi.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author codejuzi
 */
@Data
public class InterfaceInfoVO implements Serializable {

    private static final long serialVersionUID = 6694819286098833404L;

    /**
     * 接口id，主键
     */
    private Long id;

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 接口地址
     */
    private String apiUrl;

    /**
     * 接口参数（json格式）
     */
    private String reqParam;

    /**
     * 接口请求类型（0-Get, 1-Post, 2-Put, 3-Delete, ……）
     */
    private Integer reqMethod;

    /**
     * 请求头（json格式）
     */
    private String reqHeader;

    /**
     * 响应头（json格式）
     */
    private String respHeader;

    /**
     * 接口状态（0 - 下线， 1 - 上线）
     */
    private Integer apiStatus;

    /**
     * 接口创建人id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

}
