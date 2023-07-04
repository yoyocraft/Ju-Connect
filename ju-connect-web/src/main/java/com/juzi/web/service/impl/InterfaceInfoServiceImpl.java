package com.juzi.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.juzi.common.biz.PageRequest;
import com.juzi.common.biz.StatusCode;
import com.juzi.common.constants.CommonConstants;
import com.juzi.common.constants.InterfaceInfoConstants;
import com.juzi.common.constants.UserConstants;
import com.juzi.common.util.InterfaceValidUtils;
import com.juzi.common.util.SqlUtils;
import com.juzi.common.util.ThrowUtils;
import com.juzi.model.dto.SingleIdRequest;
import com.juzi.model.dto.interface_info.*;
import com.juzi.model.entity.InterfaceInfo;
import com.juzi.model.vo.InterfaceInfoVO;
import com.juzi.model.vo.UserVO;
import com.juzi.sdk.client.MockApiClient;
import com.juzi.web.mapper.InterfaceInfoMapper;
import com.juzi.web.service.InterfaceInfoService;
import com.juzi.web.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author codejuzi
 * @description 针对表【interface_info(接口信息表)】的数据库操作Service实现
 * @createDate 2023-06-28 09:36:58
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService {

    private static final Gson GSON = new Gson();

    @Resource
    private UserService userService;

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public Long interfaceInfoEdit(InterfaceEditRequest interfaceEditRequest, boolean add, HttpServletRequest request) {
        return add ? interfaceInfoAdd(interfaceEditRequest, request) : interfaceInfoUpdate(interfaceEditRequest, request);
    }

    @Override
    public Long interfaceInfoAdd(InterfaceEditRequest interfaceEditRequest, HttpServletRequest request) {
        String apiName = interfaceEditRequest.getApiName();
        String apiUrl = interfaceEditRequest.getApiUrl();
        String reqParam = interfaceEditRequest.getReqParam();
        Integer reqMethod = interfaceEditRequest.getReqMethod();
        String reqHeader = interfaceEditRequest.getReqHeader();
        String respHeader = interfaceEditRequest.getRespHeader();

        InterfaceValidUtils.validEdit(apiName, apiUrl, reqParam, reqMethod, reqHeader, respHeader, true);

        // 获取当前登录用户
        UserVO loginUser = userService.getLoginUser(request);
        Long userId = loginUser.getId();

        // 新增
        InterfaceInfo interfaceInfo = new InterfaceInfo(apiName, apiUrl, reqParam,
                reqMethod, reqHeader, respHeader, userId);

        boolean saveRes = this.save(interfaceInfo);

        ThrowUtils.throwIf(!saveRes, StatusCode.SYSTEM_ERROR, "新增接口失败");

        return interfaceInfo.getId();
    }

    @Override
    public Long interfaceInfoUpdate(InterfaceEditRequest interfaceEditRequest, HttpServletRequest request) {
        Long id = interfaceEditRequest.getId();
        String apiName = interfaceEditRequest.getApiName();
        String apiUrl = interfaceEditRequest.getApiUrl();
        String reqParam = interfaceEditRequest.getReqParam();
        Integer reqMethod = interfaceEditRequest.getReqMethod();
        String reqHeader = interfaceEditRequest.getReqHeader();
        String respHeader = interfaceEditRequest.getRespHeader();
        Long userId = interfaceEditRequest.getUserId();
        Integer apiStatus = interfaceEditRequest.getApiStatus();

        InterfaceValidUtils.validEdit(apiName, apiUrl, reqParam, reqMethod, reqHeader, respHeader, false);

        checkEditAuth(request, userId);
        // 修改
        LambdaUpdateWrapper<InterfaceInfo> updateWrapper = getUpdateWrapper(id, apiName, apiUrl, reqParam, reqMethod, reqHeader, respHeader, userId, apiStatus, request);

        boolean updateRes = this.update(updateWrapper);
        ThrowUtils.throwIf(!updateRes, StatusCode.SYSTEM_ERROR, "修改失败");
        return id;
    }

    @Override
    public Boolean interfaceInfoDelete(InterfaceDeleteRequest interfaceDeleteRequest, HttpServletRequest request) {
        Long id = interfaceDeleteRequest.getId();
        Long userId = interfaceDeleteRequest.getUserId();

        checkEditAuth(request, userId);

        return this.removeById(id);
    }

    @Override
    public Page<InterfaceInfoVO> listInterfaceByPage(PageRequest pageRequest) {
        Long current = pageRequest.getCurrent();
        Long pageSize = pageRequest.getPageSize();
        String sortedField = pageRequest.getSortedField();
        String sortOrder = pageRequest.getSortOrder();

        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();

        queryWrapper.orderBy(SqlUtils.validSortField(sortedField),
                CommonConstants.SORT_ORDER_ASC.equals(sortOrder), sortedField);


        return getInterfaceVOPage(current, pageSize, queryWrapper);
    }

    @Override
    public Page<InterfaceInfoVO> queryInterfaceByPage(InterfaceQueryRequest interfaceQueryRequest) {

        Long current = interfaceQueryRequest.getCurrent();
        Long pageSize = interfaceQueryRequest.getPageSize();
        QueryWrapper<InterfaceInfo> queryWrapper = getCommonQueryWrapper(interfaceQueryRequest);

        return getInterfaceVOPage(current, pageSize, queryWrapper);
    }



    @Override
    public Boolean interfaceOnline(SingleIdRequest idRequest) {
        Long interfaceId = idRequest.getId();
        ThrowUtils.throwIf(interfaceId <= 0, StatusCode.PARAMS_ERROR, "参数错误");

        InterfaceInfo oldInterfaceInfo = this.getById(interfaceId);
        ThrowUtils.throwIf(Objects.isNull(oldInterfaceInfo), StatusCode.NOT_FOUND_ERROR, "接口不存在");

        // 判断接口是否可以被调用，实际上是根据RPC远程，此处先使用SDK
        MockApiClient mockApiClient = new MockApiClient("aaa", "bbb");
        com.juzi.sdk.model.entity.User mockUser = new com.juzi.sdk.model.entity.User("橘子");
        String rpcRes = mockApiClient.getUserByJson(mockUser);
        ThrowUtils.throwIf(StringUtils.isBlank(rpcRes), StatusCode.SYSTEM_ERROR, "接口调用失败");

        // 修改接口状态
        boolean updateRes = interfaceInfoMapper.setApiStatusBoolean(interfaceId, InterfaceInfoConstants.ON_LINE);
        ThrowUtils.throwIf(!updateRes, StatusCode.SYSTEM_ERROR, "修改接口状态失败");

        return Boolean.TRUE;
    }

    @Override
    public Boolean interfaceOffline(SingleIdRequest idRequest) {
        Long interfaceId = idRequest.getId();
        ThrowUtils.throwIf(interfaceId <= 0, StatusCode.PARAMS_ERROR, "参数错误");

        InterfaceInfo oldInterfaceInfo = this.getById(interfaceId);
        ThrowUtils.throwIf(Objects.isNull(oldInterfaceInfo), StatusCode.NOT_FOUND_ERROR, "接口不存在");

        // 修改接口状态
        boolean updateRes = interfaceInfoMapper.setApiStatusBoolean(interfaceId, InterfaceInfoConstants.OFF_LINE);
        ThrowUtils.throwIf(!updateRes, StatusCode.SYSTEM_ERROR, "修改接口状态失败");

        return Boolean.TRUE;
    }

    @Override
    public Object invokeInterface(InterfaceInvokeRequest interfaceInvokeRequest, HttpServletRequest request) {
        // 统计次数使用
        UserVO loginUser = userService.getLoginUser(request);

        Long id = interfaceInvokeRequest.getId();
        String reqParams = interfaceInvokeRequest.getReqParams();

        InterfaceInfo interfaceInfo = this.getById(id);
        ThrowUtils.throwIf(Objects.isNull(interfaceInfo), StatusCode.NOT_FOUND_ERROR, "接口不存在");
        ThrowUtils.throwIf(!InterfaceInfoConstants.ON_LINE.equals(interfaceInfo.getApiStatus()),
                StatusCode.NO_AUTH_ERROR, "接口不在线");

        // 调用接口（本质上是RPC）
        com.juzi.sdk.model.entity.User mockUser = GSON.fromJson(reqParams, com.juzi.sdk.model.entity.User.class);
        MockApiClient mockApiClient = new MockApiClient(loginUser.getAccessKey(), loginUser.getSecretKey());

        return mockApiClient.getUserByJson(mockUser);
    }

    private Page<InterfaceInfoVO> getInterfaceVOPage(Long current, Long pageSize, QueryWrapper<InterfaceInfo> queryWrapper) {
        Page<InterfaceInfo> interfaceInfoPage = this.page(new Page<>(current, pageSize), queryWrapper);
        // 封装
        Page<InterfaceInfoVO> interfaceInfoVOPage = new Page<>(current, pageSize, interfaceInfoPage.getTotal());
        interfaceInfoVOPage.setRecords(
                interfaceInfoPage.getRecords().stream()
                        .map(InterfaceInfo::parse2VO).collect(Collectors.toList())
        );
        return interfaceInfoVOPage;
    }


    private void checkEditAuth(HttpServletRequest request, Long userId) {
        ThrowUtils.throwIf(userId <= 0, StatusCode.PARAMS_ERROR, "参数错误");
        UserVO loginUser = userService.getLoginUser(request);
        Long loginUserId = loginUser.getId();
        // 管理员 || 本人
        boolean isAdmin = UserConstants.ADMIN_ROLE.equals(loginUser.getUserRole());
        boolean isMe = loginUserId.equals(userId);
        ThrowUtils.throwIf(!(isAdmin || isMe), StatusCode.NO_AUTH_ERROR, "没有权限");
    }

    private QueryWrapper<InterfaceInfo> getCommonQueryWrapper(InterfaceQueryRequest interfaceQueryRequest) {
        String apiName = interfaceQueryRequest.getApiName();
        Integer reqMethod = interfaceQueryRequest.getReqMethod();
        Long userId = interfaceQueryRequest.getUserId();
        String sortedField = interfaceQueryRequest.getSortedField();
        String sortOrder = interfaceQueryRequest.getSortOrder();

        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(apiName), "apiName", apiName)
                .eq(!Objects.isNull(reqMethod) && InterfaceInfoConstants.REQ_METHOD_LIST.contains(reqMethod),
                        "reqMethod", reqMethod)
                .eq(!Objects.isNull(userId) && userId > 0, "userId", userId);
        queryWrapper.orderBy(SqlUtils.validSortField(sortedField), CommonConstants.SORT_ORDER_ASC.equals(sortOrder), sortedField);

        return queryWrapper;
    }

    private LambdaUpdateWrapper<InterfaceInfo> getUpdateWrapper(
            Long id, String apiName, String apiUrl, String reqParam,
            Integer reqMethod, String reqHeader, String respHeader, Long userId, Integer apiStatus,
            HttpServletRequest request
    ) {

        LambdaUpdateWrapper<InterfaceInfo> updateWrapper = new LambdaUpdateWrapper<>();

        updateWrapper.eq(!Objects.isNull(id) && id > 0, InterfaceInfo::getId, id)
                .eq(!Objects.isNull(userId) && userId > 0, InterfaceInfo::getUserId, userId)
                .set(StringUtils.isNotBlank(apiName), InterfaceInfo::getApiName, apiName)
                .set(StringUtils.isNotBlank(apiUrl), InterfaceInfo::getApiUrl, apiUrl);

        InterfaceInfo interfaceInfoFromDb = getById(id);

        updateWrapper.set(canSetField(interfaceInfoFromDb.getReqParam(), reqParam), InterfaceInfo::getReqParam, reqParam)
                .set(canSetField(interfaceInfoFromDb.getReqHeader(), reqHeader), InterfaceInfo::getReqHeader, reqHeader)
                .set(canSetField(interfaceInfoFromDb.getRespHeader(), respHeader), InterfaceInfo::getRespHeader, respHeader)
                .set(!Objects.isNull(reqMethod) && !interfaceInfoFromDb.getReqMethod().equals(reqMethod),
                        InterfaceInfo::getReqMethod, reqMethod);

        updateWrapper.set(canUpdateApiStatus(request, interfaceInfoFromDb.getApiStatus(), apiStatus),
                InterfaceInfo::getApiStatus, apiStatus);

        return updateWrapper;
    }

    private boolean canUpdateApiStatus(HttpServletRequest request, Integer apiStatusFromDb, Integer apiStatus) {

        // 不合法的api status
        if (Objects.isNull(apiStatus) || !InterfaceInfoConstants.API_STATUS_LIST.contains(apiStatus)) return false;
        // 相同的api status
        if (apiStatusFromDb.equals(apiStatus)) return false;

        // 管理员权限
        UserVO loginUser = userService.getLoginUser(request);
        return UserConstants.ADMIN_ROLE.equals(loginUser.getUserRole());
    }

    private boolean canSetField(String fieldFromDb, String field) {
        boolean op1 = StringUtils.isBlank(fieldFromDb) && StringUtils.isNotBlank(field);
        boolean op2 = StringUtils.isNotBlank(fieldFromDb) && StringUtils.isBlank(field);
        boolean op3 = StringUtils.isNotBlank(fieldFromDb) && StringUtils.isNotBlank(field)
                && !fieldFromDb.equals(field);
        return op1 || op2 || op3;
    }

}




