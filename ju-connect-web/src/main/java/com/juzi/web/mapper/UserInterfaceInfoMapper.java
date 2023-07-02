package com.juzi.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juzi.model.entity.UserInterfaceInfo;

/**
 * @author codejuzi
 * @description 针对表【user_interface_info(用户接口信息表)】的数据库操作Mapper
 * @createDate 2023-07-02 11:05:13
 */
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    /**
     * 根据userId和interfaceId查询UserInterfaceInfo
     *
     * @param userId      用户id
     * @param interfaceId 接口id
     * @return 用户接口关系
     */
    UserInterfaceInfo queryByUserIdAndInterfaceId(Long userId, Long interfaceId);
}




