package com.lyh.dao;

import com.lyh.BaseMapper;
import com.lyh.entity.TestScoreRule;

public interface TestScoreRuleMapper extends BaseMapper<TestScoreRule> {

    Long selectLatestScoreRulesId();


}