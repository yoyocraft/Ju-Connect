package com.juzi.model.dto.interface_info;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口信息删除请求
 *
 * @author codejuzi
 */
@Data
public class InterfaceDeleteRequest implements Serializable {

    private static final long serialVersionUID = 3926408608467253873L;

    /**
     * 接口id，主键
     */
    private Long id;

    /**
     * 接口创建人id
     */
    private Long userId;

}
