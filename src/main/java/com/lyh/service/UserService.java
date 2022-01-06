package com.lyh.service;

import com.github.pagehelper.PageInfo;
import com.lyh.entity.User;

import java.util.List;

/**
 * @author lyh
 * @ClassName UserService
 * @createTime 2021/12/16 10:59:00
 */
public interface UserService {


    User findUserById(Long id);

    int addUser(User user);

    List<User> findUserList(String username, String phone, Integer userType);

    User findUserByUsername(String username);

    int delUser(Long id);

    User editUser(User user);

    User login(User user);
}
