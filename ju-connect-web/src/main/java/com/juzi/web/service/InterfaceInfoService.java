package com.juzi.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.juzi.common.biz.PageRequest;
import com.juzi.model.dto.SingleIdRequest;
import com.juzi.model.dto.interface_info.InterfaceDeleteRequest;
import com.juzi.model.dto.interface_info.InterfaceEditRequest;
import com.juzi.model.dto.interface_info.InterfaceInvokeRequest;
import com.juzi.model.dto.interface_info.InterfaceQueryRequest;
import com.juzi.model.entity.InterfaceInfo;
import com.juzi.model.vo.InterfaceInfoVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author codejuzi
 * @description 针对表【interface_info(接口信息表)】的数据库操作Service
 * @createDate 2023-06-28 09:36:58
 */
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    /**
     * 接口信息编辑
     *
     * @param interfaceEditRequest 接口编辑请求
     * @param add                  是否是新增 true - 是
     * @param request              http request
     * @return 接口id
     */
    Long interfaceInfoEdit(InterfaceEditRequest interfaceEditRequest, boolean add, HttpServletRequest request);

    /**
     * 接口信息新增
     *
     * @param interfaceEditRequest 接口编辑请求
     * @param request              http request
     * @return 接口id
     */
    Long interfaceInfoAdd(InterfaceEditRequest interfaceEditRequest, HttpServletRequest request);

    /**
     * 接口信息更新
     *
     * @param interfaceEditRequest 接口编辑请求
     * @param request              http request
     * @return 接口id
     */
    Long interfaceInfoUpdate(InterfaceEditRequest interfaceEditRequest, HttpServletRequest request);


    /**
     * 接口信息删除
     *
     * @param interfaceDeleteRequest 接口删除请求
     * @param request                http request
     * @return true - 删除成功
     */
    Boolean interfaceInfoDelete(InterfaceDeleteRequest interfaceDeleteRequest, HttpServletRequest request);


    /**
     * 分页查询接口信息
     *
     * @param pageRequest 分页查询参数
     * @return interface info vo page
     */
    Page<InterfaceInfoVO> listInterfaceByPage(PageRequest pageRequest);

    /**
     * 查询接口信息
     *
     * @param interfaceQueryRequest 接口查询请求
     * @return interface info vo page
     */
    Page<InterfaceInfoVO> queryInterfaceByPage(InterfaceQueryRequest interfaceQueryRequest);

    /**
     * 管理员上线接口
     *
     * @param idRequest 接口id封装
     * @return true - 上线成功
     */
    Boolean interfaceOnline(SingleIdRequest idRequest);

    /**
     * 管理员下线接口
     *
     * @param idRequest 接口id封装
     * @return true - 下线成功
     */
    Boolean interfaceOffline(SingleIdRequest idRequest);

    /**
     * 调用接口
     *
     * @param interfaceInvokeRequest 接口调用请求
     * @param request                http request
     * @return 调用结果
     */
    Object invokeInterface(InterfaceInvokeRequest interfaceInvokeRequest, HttpServletRequest request);
}
