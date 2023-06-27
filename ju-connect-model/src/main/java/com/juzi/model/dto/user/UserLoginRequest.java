package com.juzi.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author codejuzi
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 5362698087465890332L;

    private String userAccount;
    private String userPassword;
}
