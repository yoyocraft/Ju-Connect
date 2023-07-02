package com.juzi.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户接口信息表
 *
 * @TableName user_interface_info
 */
@TableName(value = "user_interface_info")
@Data
@NoArgsConstructor
public class UserInterfaceInfo implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 接口开通人id
     */
    private Long userId;

    /**
     * 开通接口id
     */
    private Long interfaceId;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

    /**
     * 状态（0 - 正常， 1 - 禁用）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除标志（0-未删除，1-已删除）
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public UserInterfaceInfo(Long userId, Long interfaceId, Integer applyNum) {
        this.userId = userId;
        this.interfaceId = interfaceId;
        this.totalNum = applyNum;
        this.leftNum = applyNum;
    }

    public void addInterfaceAccessNum(Integer applyNum) {
        this.leftNum += applyNum;
        this.totalNum += applyNum;
    }

    public void subInterfaceAccessNum(Integer accNum) {
        this.leftNum -= accNum;
    }
}