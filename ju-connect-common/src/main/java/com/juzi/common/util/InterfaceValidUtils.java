package com.juzi.common.util;

import com.google.gson.*;
import com.juzi.common.biz.StatusCode;
import com.juzi.common.constants.InterfaceInfoConstants;
import org.apache.commons.lang3.StringUtils;

/**
 * 接口信息校验类
 *
 * @author codejuzi
 */
public class InterfaceValidUtils {

    private static final Gson GSON = new Gson();


    /**
     * 校验编辑参数是否合法
     *
     * @param apiName    接口名称
     * @param apiUrl     接口地址
     * @param reqParam   接口请求参数
     * @param reqMethod  接口请求方法
     * @param reqHeader  请求头
     * @param respHeader 响应头
     * @param add        是否是新增
     */
    public static void validEdit(String apiName, String apiUrl, String reqParam,
                                 Integer reqMethod, String reqHeader, String respHeader,
                                 boolean add) {
        // 新增参数必须不为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(apiName, apiUrl), StatusCode.PARAMS_ERROR, "参数为空");
        }
        ThrowUtils.throwIf(!validReqMethod(reqMethod), StatusCode.PARAMS_ERROR, "请求方法不合法");
        ThrowUtils.throwIf(!validReqParam(reqParam), StatusCode.PARAMS_ERROR, "请求参数不合法");
        ThrowUtils.throwIf(!validHeader(reqHeader), StatusCode.PARAMS_ERROR, "请求头不合法");
        ThrowUtils.throwIf(!validHeader(respHeader), StatusCode.PARAMS_ERROR, "响应头不合法");
    }

    private static boolean validReqMethod(Integer reqMethod) {
        return InterfaceInfoConstants.REQ_METHOD_LIST.contains(reqMethod);
    }

    private static boolean validReqParam(String reqParam) {
        if (StringUtils.isBlank(reqParam)) return true;

        try {
            JsonElement jsonElement = GSON.fromJson(reqParam, JsonElement.class);

            // 不是json数组
            if (!jsonElement.isJsonArray()) return false;

            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (JsonElement element : jsonArray) {

                // 不是json对象
                if (!element.isJsonObject()) return false;

                JsonObject jsonObject = element.getAsJsonObject();

                // 缺少必要字段
                if (!jsonObject.has("type") || !jsonObject.has("arg")) return false;

                JsonElement type = jsonObject.get("type");
                JsonElement arg = jsonObject.get("arg");

                // 类型不匹配，应为原始串（字符串）
                if (!type.isJsonPrimitive() || !arg.isJsonPrimitive()) return false;
            }

            return true;
        } catch (JsonSyntaxException e) {
            return false;
        }
    }

    private static boolean validHeader(String header) {
        if (StringUtils.isBlank(header)) return true;

        try {
            // 是json
            GSON.fromJson(header, Object.class);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        }
    }
}
