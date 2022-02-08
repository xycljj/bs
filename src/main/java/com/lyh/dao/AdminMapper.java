package com.lyh.dao;

import com.lyh.BaseMapper;
import com.lyh.entity.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper extends BaseMapper<Admin> {

    List<Admin> findAdminList(@Param("searchStr") String searchStr);

    Admin findAdminById(@Param("adminId") Long adminId);
}