package com.lyh.service.impl;

import com.lyh.dao.UserFocusMapper;
import com.lyh.entity.UserFocus;
import com.lyh.service.UserFocusService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * @author lyh
 * @ClassName UserFocusServiceImpl
 * @createTime 2022/4/14
 */
@Service
public class UserFocusServiceImpl implements UserFocusService {

    @Resource
    private UserFocusMapper userFocusMapper;

    @Override
    public boolean focus(UserFocus userFocus) {
        return userFocusMapper.insert(userFocus) == 1;
    }

    @Override
    public boolean cancelFocus(UserFocus userFocus) {
        return userFocusMapper.delete(userFocus) == 1;
    }

    @Override
    public boolean isFocused(UserFocus userFocus) {
        Example example = new Example(UserFocus.class);
        example.createCriteria().andEqualTo("userId", userFocus.getUserId())
                .andEqualTo("authorId", userFocus.getAuthorId());
        return userFocusMapper.selectCountByExample(example) == 1;
    }
}
