package com.lyh.dao;

import com.lyh.BaseMapper;
import com.lyh.entity.Admin;

import java.util.List;

public interface AdminMapper extends BaseMapper<Admin> {

    List<Admin> findAdminList();

}