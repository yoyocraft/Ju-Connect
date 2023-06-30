package com.juzi.model.enums;

import com.juzi.common.constants.UserConstants;

/**
 * @author codejuzi
 */
public enum UserRoleEnums {
    ADMIN(UserConstants.ADMIN_ROLE, "admin"),
    USER(UserConstants.USER_ROLE, "user");

    private final Integer userRole;

    private final String description;

    UserRoleEnums(Integer userRole, String description) {
        this.userRole = userRole;
        this.description = description;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 根据用户角色获取枚举
     *
     * @param userRole 用户角色
     * @return 枚举
     */
    public static UserRoleEnums getUserRoleEnum(Integer userRole) {
        for (UserRoleEnums userRoleEnum : UserRoleEnums.values()) {
            if (userRoleEnum.getUserRole().equals(userRole)) {
                return userRoleEnum;
            }
        }
        return null;
    }
}
