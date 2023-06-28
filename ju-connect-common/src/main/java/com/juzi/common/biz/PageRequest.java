package com.juzi.common.biz;

import com.juzi.common.constants.CommonConstants;
import lombok.Data;

import java.io.Serializable;

/**
 * @author codejuzi
 */
@Data
public class PageRequest implements Serializable {

    private static final long serialVersionUID = 1064870233672558906L;

    private Long current = 1L;
    private Long pageSize = 10L;
    private String sortedField;
    /**
     * 排序方式，默认升序
     */
    private String sortOrder = CommonConstants.SORT_ORDER_ASC;
}
