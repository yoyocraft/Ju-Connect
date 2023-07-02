package com.juzi.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.juzi.model.dto.user_interface_info.UserInterfaceAccNumDownRequest;
import com.juzi.model.dto.user_interface_info.UserInterfaceInfoEditRequest;
import com.juzi.model.entity.UserInterfaceInfo;


/**
 * @author codejuzi
 * @description 针对表【user_interface_info(用户接口信息表)】的数据库操作Service
 * @createDate 2023-07-02 11:05:13
 */
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    /**
     * 用户开通接口或者续费接口
     *
     * @param userInterfaceInfoEditRequest 用户接口信息添加请求
     * @return id
     */
    Long userInterfaceInfoEdit(UserInterfaceInfoEditRequest userInterfaceInfoEditRequest);

    /**
     * 扣减用户接口调用剩余次数
     *
     * @param userInterfaceAccNumDownRequest 用户接口调用剩余次数封装
     * @return true - 扣减成功
     */
    Boolean userInterfaceAccNumDown(UserInterfaceAccNumDownRequest userInterfaceAccNumDownRequest);

}
