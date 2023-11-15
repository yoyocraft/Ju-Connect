package com.juzi.web.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juzi.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author codejuzi
 * @description 针对表【user(用户表)】的数据库操作Mapper
 * @createDate 2023-06-27 14:48:36
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据accessKey获取用户
     *
     * @param accessKey ak
     * @return 用户信息
     */
    User getUserByAccessKey(String accessKey);

    /**
     * 根据userID查询用户的ak和sk
     * @param userId user id
     * @return user info with access key and secret key
     */
    User getUserAkAndSkByUserId(Long userId);
}




