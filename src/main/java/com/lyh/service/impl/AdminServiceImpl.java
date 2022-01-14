package com.lyh.service.impl;

import com.lyh.dao.AdminMapper;
import com.lyh.entity.Admin;
import com.lyh.enums.DelEnum;
import com.lyh.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author lyh
 * @ClassName AdminServiceImpl
 * @createTime 2021/12/17 10:37:00
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Override
    public int addAdmin(Admin admin) {
        admin.setCreateTime(new Date());
        admin.setIsDel(DelEnum.IS_NOT_DEL.getValue());
        return adminMapper.insertSelective(admin);
    }

    @Override
    public List<Admin> findAdminList(String searchStr) {
        return adminMapper.findAdminList(searchStr);
    }

    @Override
    public Admin findAdminById(Long id) {
        return adminMapper.selectByPrimaryKey(id);
    }
}
