package com.juzi.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.juzi.model.dto.user.UserLoginRequest;
import com.juzi.model.dto.user.UserRegisterRequest;
import com.juzi.model.entity.User;
import com.juzi.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author codejuzi
 * @description 针对表【user(用户表)】的数据库操作Service
 * @createDate 2023-06-27 14:48:36
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户注册请求
     * @return 新用户ID
     */
    Long userRegister(UserRegisterRequest userRegisterRequest);

    /**
     * 用户登录
     *
     * @param userLoginRequest 用户登录请求
     * @param request          http request
     * @return user vo
     */
    UserVO userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request);

    /**
     * 用户登出
     *
     * @param request http request
     * @return true - 登出成功
     */
    Boolean userLogout(HttpServletRequest request);

    /**
     * 获取当前登录用户
     *
     * @param request http request
     * @return user vo
     */
    UserVO getLoginUser(HttpServletRequest request);

    /**
     * 判断当前登录用户是否是管理员
     *
     * @param request http request
     * @return true - 是管理员
     */
    Boolean canAdmin(HttpServletRequest request);
}
