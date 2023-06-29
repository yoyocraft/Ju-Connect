package com.juzi.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.juzi.model.vo.InterfaceInfoVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * 接口信息表
 *
 * @TableName interface_info
 */
@TableName(value = "interface_info")
@Data
@NoArgsConstructor
public class InterfaceInfo implements Serializable {
    /**
     * 接口id，主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 接口地址
     */
    private String apiUrl;

    /**
     * 接口参数（json格式）
     * [
     * {
     * "type": "String",
     * "arg": "username"
     * }
     * ]
     */
    private String reqParam;

    /**
     * 接口请求类型（0-Get, 1-Post, 2-Put, 3-Delete, ……）
     */
    private Integer reqMethod;

    /**
     * 请求头（json格式）
     */
    private String reqHeader;

    /**
     * 响应头（json格式）
     */
    private String respHeader;

    /**
     * 接口状态（0 - 下线， 1 - 上线）
     */
    private Integer apiStatus;

    /**
     * 接口创建人id
     */
    private Long userId;

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

    public InterfaceInfo(String apiName, String apiUrl, String reqParam, Integer reqMethod, String reqHeader, String respHeader, Long userId) {
        this.apiName = apiName;
        this.apiUrl = apiUrl;
        this.reqParam = reqParam;
        this.reqMethod = reqMethod;
        this.reqHeader = reqHeader;
        this.respHeader = respHeader;
        this.userId = userId;
    }

    public static InterfaceInfoVO parse2VO(InterfaceInfo interfaceInfo) {
        InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
        BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);
        return interfaceInfoVO;
    }

}