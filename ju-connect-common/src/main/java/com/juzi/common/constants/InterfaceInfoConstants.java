package com.juzi.common.constants;

import java.util.Arrays;
import java.util.List;

/**
 * 接口信息常量类
 *
 * @author codejuzi
 */
public interface InterfaceInfoConstants {

    // region req method
    Integer GET = 0;
    Integer POST = 1;
    Integer PUT = 2;
    Integer DELETE = 3;


    List<Integer> REQ_METHOD_LIST = Arrays.asList(
            InterfaceInfoConstants.GET,
            InterfaceInfoConstants.POST,
            InterfaceInfoConstants.PUT,
            InterfaceInfoConstants.DELETE
    );

    // endregion

    // region api status

    Integer ON_LINE = 1;
    Integer OFF_LINE = 0;


    List<Integer> API_STATUS_LIST = Arrays.asList(
            InterfaceInfoConstants.ON_LINE,
            InterfaceInfoConstants.OFF_LINE
    );
    // endregion
}
