package com.juzi.dubbo.service;

import com.juzi.model.entity.User;

/**
 * @author codejuzi
 */
public interface RPCUserService {

    /**
     * 根据accessKey获取用户
     *
     * @param accessKey ak
     * @return 用户信息
     */
    User getUserByAccessKey(String accessKey);
}
