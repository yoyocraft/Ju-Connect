package com.juzi.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juzi.common.biz.StatusCode;
import com.juzi.common.constants.UserConstants;
import com.juzi.common.util.BizRandomUtils;
import com.juzi.common.util.ThrowUtils;
import com.juzi.common.util.UserValidUtils;
import com.juzi.model.dto.user.UserLoginRequest;
import com.juzi.model.dto.user.UserRegisterRequest;
import com.juzi.model.entity.User;
import com.juzi.model.vo.UserVO;
import com.juzi.web.mapper.UserMapper;
import com.juzi.web.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author codejuzi
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2023-06-27 14:48:36
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Long userRegister(UserRegisterRequest userRegisterRequest) {
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        // 校验基本参数
        UserValidUtils.validRegister(userAccount, userPassword, checkPassword);

        synchronized (userAccount.intern()) {
            // 校验用户是否存在
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getUserAccount, userAccount);
            User user = userMapper.selectOne(queryWrapper);
            ThrowUtils.throwIf(!Objects.isNull(user), StatusCode.DUPLICATE_DATA, "账号已被注册");

            // 注册逻辑
            String salt = BizRandomUtils.genRandomSalt();
            String encryptPwd = DigestUtils.md5DigestAsHex((salt + userPassword).getBytes(StandardCharsets.UTF_8));
            // 插入数据
            User newUser = new User(
                    BizRandomUtils.genRandomNickName(),
                    userAccount,
                    encryptPwd,
                    salt,
                    BizRandomUtils.genRandomAvatar(),
                    BizRandomUtils.genRandomGender()
            );
            boolean saveRes = this.save(newUser);
            ThrowUtils.throwIf(!saveRes, StatusCode.SYSTEM_ERROR, "注册失败");
            return newUser.getId();
        }
    }

    @Override
    public UserVO userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request) {
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        UserValidUtils.validLogin(userAccount, userPassword);

        // 校验用户是否存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount, userAccount);
        User userFromDB = userMapper.selectOne(queryWrapper);
        ThrowUtils.throwIf(Objects.isNull(userFromDB), StatusCode.NOT_FOUND_ERROR, "账号不存在");

        // 比较密码
        String salt = userFromDB.getSalt();
        String encryptPwd = DigestUtils.md5DigestAsHex((salt + userPassword).getBytes(StandardCharsets.UTF_8));
        ThrowUtils.throwIf(!userFromDB.getUserPassword().equals(encryptPwd), StatusCode.PARAMS_ERROR, "密码错误");

        // 脱敏用户信息
        UserVO userVO = User.parse2VO(userFromDB);

        // 保存用户登录态
        request.getSession().setAttribute(UserConstants.USER_LOGIN_STATE_KEY, userVO);

        return userVO;
    }

    @Override
    public Boolean userLogout(HttpServletRequest request) {
        Object loginUserVoObj = request.getSession().getAttribute(UserConstants.USER_LOGIN_STATE_KEY);
        ThrowUtils.throwIf(Objects.isNull(loginUserVoObj), StatusCode.NOT_LOGIN_ERROR, "当前尚未登录");

        request.getSession().removeAttribute(UserConstants.USER_LOGIN_STATE_KEY);
        return Boolean.TRUE;
    }

    @Override
    public UserVO getLoginUser(HttpServletRequest request) {
        Object loginUserVoObj = request.getSession().getAttribute(UserConstants.USER_LOGIN_STATE_KEY);
        ThrowUtils.throwIf(Objects.isNull(loginUserVoObj), StatusCode.NOT_LOGIN_ERROR, "当前尚未登录");

        return (UserVO) loginUserVoObj;
    }

    @Override
    public Boolean canAdmin(HttpServletRequest request) {
        UserVO loginUser = getLoginUser(request);
        return UserConstants.ADMIN_ROLE.equals(loginUser.getUserRole());
    }

}




