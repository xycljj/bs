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

import java.util.*;
import java.util.stream.Collectors;

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
        int[] nums = {1,2,3,2,2,2,5,4,2};
        int n = nums.length / 2;
        Map<Integer,Integer> map = new HashMap<Integer,Integer>();
        for(int num : nums){
            if(map.containsKey(num)){
                Integer count = map.get(num);
                map.put(num,++count);
            }else{
                map.put(num,1);
            }
        }
        int res = 0;
        for(Map.Entry entry :map.entrySet()){
            if(((Integer)entry.getValue())>n){
               res = (Integer) entry.getKey();
            }
        }
        System.out.println(res);

    }
    
    /**
     * @Author lyh
     * @Description // 翻转单词
     * @Param 
     * @return 
     * @Date 2022/1/19
     **/
    @Test
    void reverseWords(){
        String s = " ";
        if(s.startsWith(" ")||s.endsWith(" ")){
            s.substring(1,s.length()-1);
        }
        System.out.println(s);
    }

    @Test
    void test1(){
        List<Long> list = new ArrayList<>();
        list.add(1l);
        list.add(2l);
        list.add(3l);
        list.add(4l);
        System.out.println(list);
        List<Long> list1 = new ArrayList<>();
        list1.add(4l);
        list1.add(5l);
        list1.add(6l);
        list1.add(7l);
        System.out.println(list1);
        List<Long> collect = list.stream().filter(n -> list1.stream().allMatch(b -> !n.equals(b))).collect(Collectors.toList());
        boolean b = list.stream().allMatch(n -> n != 1l);
        System.out.println(collect);
    }

}
