package com.lyh.service.impl;

import com.lyh.dao.TestScoreRuleMapper;
import com.lyh.entity.TestScoreRule;
import com.lyh.entity.vo.TestScoreRulesVo;
import com.lyh.service.TestScoreRuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lyh
 * @ClassName TestScoreRuleServiceImpl
 * @createTime 2022/5/6
 */
@Service
public class TestScoreRuleServiceImpl implements TestScoreRuleService {

    @Resource
    private TestScoreRuleMapper testScoreRuleMapper;

    @Override
    public List<TestScoreRulesVo> findListByIds(String rulesId) {
        List<TestScoreRule> testScoreRules = testScoreRuleMapper.selectByIds(rulesId);
        List<TestScoreRulesVo> list = new ArrayList<>();
        for(TestScoreRule testScoreRule : testScoreRules){
            TestScoreRulesVo testScoreRulesVo = new TestScoreRulesVo();
            testScoreRulesVo.setResult(testScoreRule.getResult());
            List<Integer> scoreList = new ArrayList<>();
            Arrays.stream(testScoreRule.getSegment().split(",")).forEach((score) -> scoreList.add(Integer.parseInt(score)));
            testScoreRulesVo.setResultScore(scoreList);
            list.add(testScoreRulesVo);
        }
        return list;
    }
}
