package com.juzi.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juzi.common.biz.StatusCode;
import com.juzi.common.util.ThrowUtils;
import com.juzi.common.util.UserInterfaceValidUtils;
import com.juzi.model.dto.user_interface_info.UserInterfaceInfoEditRequest;
import com.juzi.model.entity.UserInterfaceInfo;
import com.juzi.web.mapper.UserInterfaceInfoMapper;
import com.juzi.web.service.UserInterfaceInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author codejuzi
 * @description 针对表【user_interface_info(用户接口信息表)】的数据库操作Service实现
 * @createDate 2023-07-02 11:05:13
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
        implements UserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Override
    public Long userInterfaceInfoEdit(UserInterfaceInfoEditRequest userInterfaceInfoEditRequest) {
        Long userId = userInterfaceInfoEditRequest.getUserId();
        Long interfaceId = userInterfaceInfoEditRequest.getInterfaceId();
        Integer applyNum = userInterfaceInfoEditRequest.getApplyNum();
        UserInterfaceValidUtils.validApply(userId, interfaceId, applyNum);

        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoMapper.queryByUserIdAndInterfaceId(userId, interfaceId);
        if (Objects.isNull(userInterfaceInfo)) {
            // 新增次数
            userInterfaceInfo = new UserInterfaceInfo(userId, interfaceId, applyNum);
            boolean saveRes = this.save(userInterfaceInfo);
            ThrowUtils.throwIf(!saveRes, StatusCode.SYSTEM_ERROR, "新增用户接口信息失败");
            return userInterfaceInfo.getId();
        }
        // 修改次数
        userInterfaceInfo.addInterfaceAccessNum(applyNum);
        boolean updateRes = this.updateById(userInterfaceInfo);
        ThrowUtils.throwIf(!updateRes, StatusCode.SYSTEM_ERROR, "修改用户接口信息失败");
        return userInterfaceInfo.getId();
    }
}




