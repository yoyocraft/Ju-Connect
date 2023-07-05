package com.juzi.web.rpc;

import com.juzi.common.biz.StatusCode;
import com.juzi.common.util.ThrowUtils;
import com.juzi.common.util.UserInterfaceValidUtils;
import com.juzi.dubbo.service.RPCUserInterfaceService;
import com.juzi.model.dto.user_interface_info.UserInterfaceAccNumDownRequest;
import com.juzi.model.entity.UserInterfaceInfo;
import com.juzi.web.mapper.UserInterfaceInfoMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author codejuzi
 */
@DubboService
public class RPCUserInterfaceServiceImpl implements RPCUserInterfaceService {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Override
    public void userInterfaceAccNumDown(UserInterfaceAccNumDownRequest userInterfaceAccNumDownRequest) {
        Long userId = userInterfaceAccNumDownRequest.getUserId();
        Long interfaceId = userInterfaceAccNumDownRequest.getInterfaceId();
        Integer accNum = userInterfaceAccNumDownRequest.getAccNum();

        UserInterfaceValidUtils.validAcc(userId, interfaceId, accNum);

        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoMapper.queryByUserIdAndInterfaceId(userId, interfaceId);

        ThrowUtils.throwIf(Objects.isNull(userInterfaceInfo), StatusCode.NOT_FOUND_ERROR, "未开通此接口");
        // 判断是否还有调用次数
        Integer leftNum = userInterfaceInfo.getLeftNum();
        ThrowUtils.throwIf(leftNum < accNum, StatusCode.NO_AUTH_ERROR, "用户接口调用次数不足");

        boolean updateRes = userInterfaceInfoMapper.subAccNum(userId, interfaceId, accNum);
        ThrowUtils.throwIf(!updateRes, StatusCode.SYSTEM_ERROR, "修改用户接口信息失败");
    }
}
