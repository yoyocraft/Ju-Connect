package com.juzi.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求
 * @author codejuzi
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 4870433762053590687L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
