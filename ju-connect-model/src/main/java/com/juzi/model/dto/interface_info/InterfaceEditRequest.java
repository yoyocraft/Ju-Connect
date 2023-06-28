package com.juzi.model.dto.interface_info;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口信息编辑请求（新增、修改）
 *
 * @author codejuzi
 */
@Data
public class InterfaceEditRequest implements Serializable {

    private static final long serialVersionUID = 3926408608467253873L;

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
     * 接口创建人id
     */
    private Long userId;

    /**
     * 接口状态（0 - 下线， 1 - 上线）
     */
    private Integer apiStatus;
}
