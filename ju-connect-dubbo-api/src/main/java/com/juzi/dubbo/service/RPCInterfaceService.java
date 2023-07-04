package com.juzi.dubbo.service;

import com.juzi.model.dto.interface_info.InterfaceGatewayQueryRequest;
import com.juzi.model.entity.InterfaceInfo;

/**
 * @author codejuzi
 */
public interface RPCInterfaceService {

    /**
     * 网关层面去搜索接口
     *
     * @param interfaceGatewayQueryRequest 网关搜索接口请求封装
     * @return interface
     */
    InterfaceInfo queryInterfaceByGateway(InterfaceGatewayQueryRequest interfaceGatewayQueryRequest);
}
