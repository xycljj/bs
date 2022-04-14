package com.lyh.service.impl;

import com.lyh.dao.AdminMapper;
import com.lyh.dao.AdministratorOperationInformationMapper;
import com.lyh.dao.UserMapper;
import com.lyh.entity.Admin;
import com.lyh.entity.AdministratorOperationInformation;
import com.lyh.entity.User;
import com.lyh.enums.DelEnum;
import com.lyh.service.UserService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author lyh
 * @ClassName UserServiceImpl
 * @createTime 2021/12/16 11:00:00
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private AdministratorOperationInformationMapper administratorOperationInformationMapper;

    @Resource
    private AdminMapper adminMapper;


    @Override
    public User findUserById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int addUser(User user) {
        user.setIsDel(DelEnum.IS_NOT_DEL.getValue());
        user.setCreateTime(new Date());
        return userMapper.insertSelective(user);
    }

    @Override
    public List<User> findUserList(String username, String phone) {
        return userMapper.findUserList(username, phone);
    }

    @Override
    public User findUserByUsername(String username) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username", username).andEqualTo("isDel", DelEnum.IS_NOT_DEL.getValue());
        return userMapper.selectOneByExample(example);
    }

    @Override
    public int delUser(Long id) {
        User user = new User();
        user.setId(id);
        user.setIsDel(DelEnum.IS_DEL.getValue());
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User editUser(User user, Admin admin) {
        if (admin == null) {
            userMapper.updateByPrimaryKeySelective(user);
        }else{
            userMapper.updateByPrimaryKeySelective(user);
            //添加操作记录
            AdministratorOperationInformation administratorOperationInformation = new AdministratorOperationInformation();
            administratorOperationInformation.setAdminId(admin.getId());
            administratorOperationInformation.setAdminName(admin.getAdminName());
            administratorOperationInformation.setMessage(admin.getAdminName() + " 修改了 " + user.getName() + " 的信息");
            administratorOperationInformationMapper.insert(administratorOperationInformation);
        }
        return user;
    }

    @Override
    public User login(User user) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username", user.getUsername())
                .andEqualTo("password", user.getPassword())
                .andEqualTo("isDel", DelEnum.IS_NOT_DEL.getValue());
        User user1 = userMapper.selectOneByExample(example);
        if (user1 == null) {
            return null;
        }
        return user1;
    }

    @Override
    public void changeUserInfo(Long userId, String url) {
        User user = userMapper.selectByPrimaryKey(userId);
        user.setAvatar(url);
        userMapper.updateByPrimaryKeySelective(user);
    }
}
