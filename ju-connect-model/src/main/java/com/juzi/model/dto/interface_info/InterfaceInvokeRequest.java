package com.juzi.model.dto.interface_info;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口在线调用请求
 *
 * @author codejuzi
 */
@Data
public class InterfaceInvokeRequest implements Serializable {

    private static final long serialVersionUID = 5804019427603979775L;

    private Long id;

    /**
     * 用户请求参数（Json串）
     */
    private String reqParams;
}
