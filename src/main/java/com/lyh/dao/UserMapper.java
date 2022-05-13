package com.lyh.dao;

import com.lyh.BaseMapper;
import com.lyh.entity.User;
import com.lyh.entity.vo.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    List<User> findUserList(@Param("username") String username, @Param("phone") String phone);

    List<User> selectCloudList(@Param("list") List<Long> list);

    List<User> selectMyFocusUsers(@Param("userId") Long userId);

    List<UserInfo> selectConsultantList(@Param("username") String username, @Param("skill") Long skillFieldId);
}