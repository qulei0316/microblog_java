package com.qulei.dao;

import com.qulei.bean.dto.UserDto;
import com.qulei.bean.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    //根据手机号查询用户
    User getUserByphone(@Param("phone")String phone);

    //根据邮箱查询用户
    User getUserByemail(@Param("email")String email);

    //更新用户信息
    int updateUser(UserDto userDto);

    //新增用户
    int addUser(UserDto dto);

    //根据id查找用户
    User getUserById(@Param("id") String user_id);

    //用户登出
    int logout(UserDto userDto);

    User getUserByname(@Param("name") String username);
}
