package com.juzi.web.controller;

import com.juzi.common.annotation.AuthCheck;
import com.juzi.common.biz.BaseResponse;
import com.juzi.common.util.ResultUtils;
import com.juzi.model.dto.user_interface_info.UserInterfaceAccNumDownRequest;
import com.juzi.model.dto.user_interface_info.UserInterfaceInfoEditRequest;
import com.juzi.web.service.UserInterfaceInfoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author codejuzi
 */
@RestController
public class UserInterfaceInfoController {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @PostMapping("/apply")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Long> userInterfaceApply(@RequestBody UserInterfaceInfoEditRequest userInterfaceInfoEditRequest) {
        return ResultUtils.success(userInterfaceInfoService.userInterfaceInfoEdit(userInterfaceInfoEditRequest));
    }
}
