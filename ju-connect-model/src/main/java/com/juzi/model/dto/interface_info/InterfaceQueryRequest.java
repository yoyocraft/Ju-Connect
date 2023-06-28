package com.juzi.model.dto.interface_info;

import com.juzi.common.biz.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 接口信息查询请求
 *
 * @author codejuzi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceQueryRequest extends PageRequest
        implements Serializable {

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
     * 接口请求类型（0-Get, 1-Post, 2-Put, 3-Delete, ……）
     */
    private Integer reqMethod;

    /**
     * 接口状态（0 - 下线， 1 - 上线）
     */
    private Integer apiStatus;

    /**
     * 接口创建人id
     */
    private Long userId;
}
