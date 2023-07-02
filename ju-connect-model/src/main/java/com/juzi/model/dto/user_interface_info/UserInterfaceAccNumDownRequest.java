package com.juzi.model.dto.user_interface_info;

import lombok.Data;

import java.io.Serializable;

/**
 * @author codejuzi
 */
@Data
public class UserInterfaceAccNumDownRequest implements Serializable {

    private static final long serialVersionUID = -7413794566216061903L;

    /**
     * 接口开通人id
     */
    private Long userId;

    /**
     * 开通接口id
     */
    private Long interfaceId;

    /**
     * 申请调用次数
     */
    private Integer accNum;
}
