package com.juzi.web.rpc;

import com.juzi.dubbo.service.RPCUserService;
import com.juzi.model.entity.User;
import com.juzi.web.mapper.UserMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author codejuzi
 */
@DubboService
public class RPCUserServiceImpl implements RPCUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserByAccessKey(String accessKey) {
        return userMapper.getUserByAccessKey(accessKey);
    }
}
