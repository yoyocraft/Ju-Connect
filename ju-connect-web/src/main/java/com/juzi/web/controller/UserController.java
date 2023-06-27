package com.juzi.web.controller;


import com.juzi.common.biz.BaseResponse;
import com.juzi.common.util.ResultUtils;
import com.juzi.model.dto.user.UserLoginRequest;
import com.juzi.model.dto.user.UserRegisterRequest;
import com.juzi.model.vo.UserVO;
import com.juzi.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * @author codejuzi
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        Long userId = userService.userRegister(userRegisterRequest);
        return ResultUtils.success(userId);
    }

    @PostMapping("/login")
    public BaseResponse<UserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        return ResultUtils.success(userService.userLogin(userLoginRequest, request));
    }

    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        return ResultUtils.success(userService.userLogout(request));
    }

    @GetMapping("/curr")
    public BaseResponse<UserVO> getLoginUser(HttpServletRequest request) {
        return ResultUtils.success(userService.getLoginUser(request));
    }

}
