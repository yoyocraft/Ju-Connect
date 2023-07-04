package com.juzi.model.enums;

import com.juzi.common.constants.InterfaceInfoConstants;

/**
 * @author codejuzi
 */
public enum ApiMethodEnums {
    GET(InterfaceInfoConstants.GET, "GET"),
    POST(InterfaceInfoConstants.POST, "POST"),
    PUT(InterfaceInfoConstants.PUT, "PUT"),
    DELETE(InterfaceInfoConstants.DELETE, "DELETE"),
    ;

    private final Integer apiMethod;
    private final String methodStr;



    ApiMethodEnums(Integer apiMethod, String methodStr) {
        this.apiMethod = apiMethod;
        this.methodStr = methodStr;
    }


    public String getMethodStr() {
        return methodStr;
    }

    public Integer getApiMethod() {
        return apiMethod;
    }

    public static ApiMethodEnums getEnumByMethod(String methodStr) {
        for (ApiMethodEnums methodEnum : ApiMethodEnums.values()) {
            if (methodEnum.getMethodStr().equals(methodStr)) {
                return methodEnum;
            }
        }
        return null;
    }

}
