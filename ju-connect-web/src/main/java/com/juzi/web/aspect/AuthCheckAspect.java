package com.juzi.web.aspect;

import com.juzi.common.annotation.AuthCheck;
import com.juzi.common.biz.StatusCode;
import com.juzi.common.util.ThrowUtils;
import com.juzi.model.enums.UserRoleEnums;
import com.juzi.model.vo.UserVO;
import com.juzi.web.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;


/**
 * 权限校验AOP
 *
 * @author codejuzi
 */
@Aspect
@Component
public class AuthCheckAspect {

    @Resource
    private UserService userService;

    @Around("@annotation(authCheck)")
    public Object doAuthCheck(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        // 获取必须的权限
        String mustRole = authCheck.mustRole();
        if (StringUtils.isNotBlank(mustRole)) {
            // 获取当前登录用户
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            assert requestAttributes != null;
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            UserVO loginUser = userService.getLoginUser(request);
            ThrowUtils.throwIf(Objects.isNull(loginUser), StatusCode.NO_AUTH_ERROR, "当前尚未登录");
            UserRoleEnums userRoleEnum = UserRoleEnums.getUserRoleEnum(loginUser.getUserRole());
            ThrowUtils.throwIf(Objects.isNull(userRoleEnum), StatusCode.NO_AUTH_ERROR, "当前用户角色不存在");
            // 校验必须的角色
            ThrowUtils.throwIf(!userRoleEnum.getDescription().equals(mustRole), StatusCode.NO_AUTH_ERROR, "当前用户角色不在必须的角色中");
        }
        // 通过校验
        return joinPoint.proceed();
    }

}
