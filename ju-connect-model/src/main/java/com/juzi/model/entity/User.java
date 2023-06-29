package com.juzi.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.juzi.model.vo.UserVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * 用户表
 *
 * @author codejuzi
 * @TableName user
 */
@TableName(value = "user")
@Data
@NoArgsConstructor
public class User implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 用户角色：0 - user / 1 - admin
     */
    private Integer userRole;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * ak
     */
    private String accessKey;

    /**
     * sk
     */
    private String secretKey;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public User(String nickname, String userAccount, String userPassword, String salt, String userAvatar, Integer gender) {
        this.nickname = nickname;
        this.userAccount = userAccount;
        this.salt = salt;
        this.userAvatar = userAvatar;
        this.userPassword = userPassword;
        this.gender = gender;
    }

    public static UserVO parse2VO(User originUser) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(originUser, userVO);
        return userVO;
    }
}