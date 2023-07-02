package com.juzi.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juzi.model.entity.InterfaceInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author codejuzi
 * @description 针对表【interface_info(接口信息表)】的数据库操作Mapper
 * @createDate 2023-06-28 09:36:58
 */
@Mapper
public interface InterfaceInfoMapper extends BaseMapper<InterfaceInfo> {

    /**
     * 修改接口状态
     *
     * @param id        接口id
     * @param apiStatus 待修改的接口状态
     * @return true - 修改成功
     */
    boolean setApiStatusBoolean(Long id, Integer apiStatus);
}




