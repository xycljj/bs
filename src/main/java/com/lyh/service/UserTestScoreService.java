package com.lyh.service;

import com.github.pagehelper.PageInfo;
import com.lyh.entity.UserTestScore;
import com.lyh.entity.vo.UserTestScoreVo;

import java.util.List;

/**
 * @author lyh
 * @ClassName UserTestScoreService
 * @createTime 2022/5/5
 */
public interface UserTestScoreService {
    void doTest(UserTestScore userTestScore);

    PageInfo<UserTestScoreVo> findTestScoreListById(Long userId, Long testId, Integer pageIndex, Integer pageSize);
}
