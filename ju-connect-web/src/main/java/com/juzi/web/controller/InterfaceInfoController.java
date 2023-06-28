package com.juzi.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.juzi.common.biz.BaseResponse;
import com.juzi.common.biz.PageRequest;
import com.juzi.common.util.ResultUtils;
import com.juzi.model.dto.interface_info.InterfaceDeleteRequest;
import com.juzi.model.dto.interface_info.InterfaceEditRequest;
import com.juzi.model.dto.interface_info.InterfaceQueryRequest;
import com.juzi.model.vo.InterfaceInfoVO;
import com.juzi.web.service.InterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author codejuzi
 */
@Slf4j
@RestController
@RequestMapping("/interface")
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceEditRequest interfaceEditRequest, HttpServletRequest request) {
        return ResultUtils.success(
                interfaceInfoService.interfaceInfoEdit(interfaceEditRequest, true, request),
                "新增成功"
        );
    }

    @PutMapping("/update")
    public BaseResponse<Long> updateInterfaceInfo(@RequestBody InterfaceEditRequest interfaceEditRequest, HttpServletRequest request) {
        return ResultUtils.success(
                interfaceInfoService.interfaceInfoEdit(interfaceEditRequest, false, request),
                "修改成功"
        );
    }

    @DeleteMapping("/del")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody InterfaceDeleteRequest interfaceDeleteRequest, HttpServletRequest request) {
        return ResultUtils.success(interfaceInfoService.interfaceInfoDelete(interfaceDeleteRequest, request));
    }

    @GetMapping("/list")
    public BaseResponse<Page<InterfaceInfoVO>> listInterfaceInfoByPage(PageRequest pageRequest) {
        return ResultUtils.success(interfaceInfoService.listInterfaceByPage(pageRequest));
    }

    @GetMapping("/query")
    public BaseResponse<Page<InterfaceInfoVO>> queryInterfaceInfoByPage(InterfaceQueryRequest interfaceQueryRequest) {
        return ResultUtils.success(interfaceInfoService.queryInterfaceByPage(interfaceQueryRequest));
    }

}
