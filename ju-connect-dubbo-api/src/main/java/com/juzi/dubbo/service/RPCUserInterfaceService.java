package com.juzi.dubbo.service;

import com.juzi.model.dto.user_interface_info.UserInterfaceAccNumDownRequest;

/**
 * @author codejuzi
 */
public interface RPCUserInterfaceService {

    /**
     * 扣减用户接口调用次数
     *
     * @param userInterfaceAccNumDownRequest 用户接口调用次数扣减请求
     */
    void userInterfaceAccNumDown(UserInterfaceAccNumDownRequest userInterfaceAccNumDownRequest);
}
