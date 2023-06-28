package com.juzi.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author codejuzi
 */
public class SqlUtils {
    /**
     * 校验排序字段是否合法（防止 SQL 注入）
     *
     * @param sortField 排序字段
     * @return true - 合法
     */
    public static boolean validSortField(String sortField) {
        if (StringUtils.isBlank(sortField)) {
            return false;
        }
        return !StringUtils.containsAny(sortField, "=", "(", ")", " ");
    }
}
