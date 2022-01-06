package com.lyh;

import com.lyh.controller.UserController;
import com.lyh.dao.UserMapper;
import com.lyh.entity.User;
import com.lyh.enums.DelEnum;
import com.lyh.service.UserService;
import com.lyh.service.impl.UserServiceImpl;
import com.lyh.utils.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserController userController;
    @Autowired
    private UserService userService;
    @Test
    void contextLoads() {
        User user = new User();
        user.setUsername("hzdd");
        user.setPassword("123456");
        user.setPhoneNumber("123456789");
        user.setName("李宇豪");
        user.setIsDel(DelEnum.IS_NOT_DEL.getValue());
        userMapper.insertSelective(user);
    }

    @Test
    void test(){
//        List<User> userList = userService.findUserList();
//        System.out.println(userList);
    }

}
