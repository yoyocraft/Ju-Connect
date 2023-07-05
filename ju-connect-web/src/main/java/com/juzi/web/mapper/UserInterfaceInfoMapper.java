package com.juzi.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juzi.model.entity.UserInterfaceInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author codejuzi
 * @description 针对表【user_interface_info(用户接口信息表)】的数据库操作Mapper
 * @createDate 2023-07-02 11:05:13
 */
@Mapper
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    /**
     * 根据userId和interfaceId查询UserInterfaceInfo
     *
     * @param userId      用户id
     * @param interfaceId 接口id
     * @return 用户接口关系
     */
    UserInterfaceInfo queryByUserIdAndInterfaceId(Long userId, Long interfaceId);

    /**
     * 扣减用户调用接口数
     *
     * @param userId      用户id
     * @param interfaceId 接口id
     * @param accNum      扣减调用次数
     * @return true - 修改成功
     */
    boolean subAccNum(Long userId, Long interfaceId, Integer accNum);
}




