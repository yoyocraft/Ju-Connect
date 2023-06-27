package com.juzi.common.constants;

/**
 * @author codejuzi
 */
public interface UserConstants {

    // region user info valid
    int MIN_ACC_LEN = 5;
    int MAX_ACC_LEN = 10;
    int MIN_PWD_LEN = 9;
    String ACC_PATTEN = "^[a-zA-Z0-9]+$";

    // endregion

    // region user info rules
    int SALT_LEN = 5;
    int DEFAULT_NICK_SUFFIX_LEN = 8;
    String DEFAULT_NICK_PREFIX = "user_";

    // endregion

    // region biz
    String USER_LOGIN_STATE_KEY = "user_login";

    // endregion

    // region user role

    Integer ADMIN_ROLE = 1;

    // endregion
}
