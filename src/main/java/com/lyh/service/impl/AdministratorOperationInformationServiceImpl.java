package com.lyh.service.impl;

import com.lyh.dao.AdminMapper;
import com.lyh.dao.AdministratorOperationInformationMapper;
import com.lyh.dao.UserMapper;
import com.lyh.entity.Admin;
import com.lyh.entity.AdministratorOperationInformation;
import com.lyh.entity.User;
import com.lyh.service.AdministratorOperationInformationService;
import com.lyh.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author lyh
 * @ClassName AdministratorOperationInformationServiceImpl
 * @createTime 2022/2/7
 */
@Service
public class AdministratorOperationInformationServiceImpl implements AdministratorOperationInformationService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private AdministratorOperationInformationMapper administratorOperationInformationMapper;

    @Override
    public void addRecord(Long id, Long adminId) {
        User user = userMapper.selectByPrimaryKey(id);
        Admin admin = adminMapper.selectByPrimaryKey(adminId);
        AdministratorOperationInformation administratorOperationInformation = new AdministratorOperationInformation();
        administratorOperationInformation.setAdminId(adminId);
        administratorOperationInformation.setAdminName(admin.getAdminName());
        administratorOperationInformation.setMessage(admin.getAdminName()+"删除了<"+user.getName()+">的信息");
        administratorOperationInformation.setCreateTime(new Date());
        administratorOperationInformationMapper.insert(administratorOperationInformation);
    }
}
