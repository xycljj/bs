package com.lyh.service;

import com.lyh.entity.Admin;

import java.util.List;

/**
 * @author lyh
 * @ClassName AdminService
 * @createTime 2021/12/17 10:37:00
 */
public interface AdminService {

    int addAdmin(Admin admin);

    List<Admin> findAdminList(String searchStr);

    Admin findAdminById(Long id);

    Admin login(Admin admin);
}
