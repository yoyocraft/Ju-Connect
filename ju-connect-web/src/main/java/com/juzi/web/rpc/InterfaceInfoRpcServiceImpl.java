package com.juzi.web.rpc;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.juzi.common.constants.InterfaceInfoConstants;
import com.juzi.model.dto.interface_info.InterfaceGatewayQueryRequest;
import com.juzi.model.entity.InterfaceInfo;
import com.juzi.rpc.InterfaceInfoRpcService;
import com.juzi.web.mapper.InterfaceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author codejuzi
 */
@DubboService
public class InterfaceInfoRpcServiceImpl implements InterfaceInfoRpcService {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public InterfaceInfo queryInterfaceByGateway(InterfaceGatewayQueryRequest interfaceGatewayQueryRequest) {
        String apiUrl = interfaceGatewayQueryRequest.getApiUrl();
        Integer apiMethod = interfaceGatewayQueryRequest.getApiMethod();
        LambdaQueryWrapper<InterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(StringUtils.isNotBlank(apiUrl), InterfaceInfo::getApiUrl, apiUrl)
                .eq(InterfaceInfoConstants.REQ_METHOD_LIST.contains(apiMethod), InterfaceInfo::getReqMethod, apiMethod);

        return interfaceInfoMapper.selectOne(queryWrapper);
    }
}
