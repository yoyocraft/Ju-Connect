package com.juzi.common.util;

import com.juzi.common.biz.StatusCode;
import com.juzi.common.constants.UserInterfaceInfoConstants;

/**
 * @author codejuzi
 */
public class UserInterfaceValidUtils {

    public static void validApply(Long userId, Long interfaceId, Integer applyNum) {
        ThrowUtils.throwIf(userId <= 0, StatusCode.PARAMS_ERROR, "参数错误");
        ThrowUtils.throwIf(interfaceId <= 0, StatusCode.PARAMS_ERROR, "参数错误");
        ThrowUtils.throwIf(applyNum <= 0 || applyNum > UserInterfaceInfoConstants.ONCE_MAX_APPLY_NUM,
                StatusCode.PARAMS_ERROR, "参数错误");
    }

    public static void validAcc(Long userId, Long interfaceId, Integer accNum) {
        ThrowUtils.throwIf(userId <= 0, StatusCode.PARAMS_ERROR, "参数错误");
        ThrowUtils.throwIf(interfaceId <= 0, StatusCode.PARAMS_ERROR, "参数错误");
        ThrowUtils.throwIf(accNum <= 0, StatusCode.PARAMS_ERROR, "参数错误");
    }
}
