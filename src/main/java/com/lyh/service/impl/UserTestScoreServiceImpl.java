package com.lyh.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyh.dao.UserTestScoreMapper;
import com.lyh.entity.UserTestScore;
import com.lyh.entity.vo.UserTestScoreVo;
import com.lyh.service.UserTestScoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lyh
 * @ClassName UserTestScoreServiceImpl
 * @createTime 2022/5/5
 */
@Service
public class UserTestScoreServiceImpl implements UserTestScoreService {

    @Resource
    private UserTestScoreMapper userTestScoreMapper;

    @Override
    public void doTest(UserTestScore userTestScore) {
        userTestScoreMapper.insert(userTestScore);
    }

    @Override
    public PageInfo<UserTestScoreVo> findTestScoreListById(Long userId, Long testId, Integer pageIndex, Integer pageSize) {
        Page<UserTestScoreVo> page = PageHelper.startPage(pageIndex, pageSize);
        List<UserTestScoreVo> list = userTestScoreMapper.selectByUserIdOrTestId(userId,testId);
        PageInfo<UserTestScoreVo> userTestScorePageInfo = page.toPageInfo();
        userTestScorePageInfo.setList(list);
        return userTestScorePageInfo;
    }
}
