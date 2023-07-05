package com.juzi.model.dto.user_interface_info;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author codejuzi
 */
@Data
@NoArgsConstructor
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

    public UserInterfaceAccNumDownRequest(Long userId, Long interfaceId, Integer accNum) {
        this.userId = userId;
        this.interfaceId = interfaceId;
        this.accNum = accNum;
    }
}
