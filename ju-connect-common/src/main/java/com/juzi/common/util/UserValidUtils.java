package com.juzi.common.util;

import com.juzi.common.biz.StatusCode;
import com.juzi.common.constants.UserConstants;
import org.apache.commons.lang3.StringUtils;

/**
 * @author codejuzi
 */
public class UserValidUtils {

    public static void validRegister(String userAccount, String userPassword, String checkPassword) {
        validLogin(userAccount, userPassword);
        ThrowUtils.throwIf(StringUtils.isBlank(checkPassword), StatusCode.PARAMS_ERROR, "参数为空");
        ThrowUtils.throwIf(!userPassword.equals(checkPassword), StatusCode.PARAMS_ERROR, "两次输入密码不一致");
    }

    public static void validLogin(String userAccount, String userPassword) {
        ThrowUtils.throwIf(StringUtils.isAnyBlank(userAccount, userPassword),
                StatusCode.PARAMS_ERROR, "参数为空");
        ThrowUtils.throwIf(!validAcc(userAccount), StatusCode.PARAMS_ERROR, "账号包含特殊字符");
        ThrowUtils.throwIf(userAccount.length() < UserConstants.MIN_ACC_LEN,
                StatusCode.PARAMS_ERROR, "账号过短");
        ThrowUtils.throwIf(userAccount.length() > UserConstants.MAX_ACC_LEN,
                StatusCode.PARAMS_ERROR, "账号过长");
        ThrowUtils.throwIf(userPassword.length() < UserConstants.MIN_PWD_LEN,
                StatusCode.PARAMS_ERROR, "密码过短");
    }

    private static boolean validAcc(String str) {
        return str.matches(UserConstants.ACC_PATTEN);
    }

}
