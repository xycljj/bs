package com.lyh.service;

import com.lyh.entity.vo.TestScoreRulesVo;

import java.util.List;

/**
 * @author lyh
 * @ClassName TestScoreRuleService
 * @createTime 2022/5/6
 */
public interface TestScoreRuleService {
    List<TestScoreRulesVo> findListByIds(String rulesId);

}
