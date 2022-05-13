package com.lyh.service.impl;

import com.lyh.dao.TestMapper;
import com.lyh.dao.TestScoreRuleMapper;
import com.lyh.dao.TestSubjectMapper;
import com.lyh.entity.Test;
import com.lyh.entity.TestScoreRule;
import com.lyh.entity.TestSubject;
import com.lyh.entity.vo.TestScoreRulesVo;
import com.lyh.entity.vo.TestSubjectVo;
import com.lyh.entity.vo.TestVo;
import com.lyh.enums.DelEnum;
import com.lyh.service.TestService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lyh
 * @ClassName TestServiceImpl
 * @createTime 2022/5/4
 */
@Service
public class TestServiceImpl implements TestService {

    @Resource
    private TestMapper testMapper;

    @Resource
    private TestSubjectMapper testSubjectMapper;

    @Resource
    private TestScoreRuleMapper testScoreRuleMapper;


    @Override
    public void addTest(TestVo testVo) {
        List<TestSubjectVo> testSubjects = testVo.getTestSubjects();
        // 测试题目持久化到数据库
        // 记录题目id
        StringBuffer itemId = new StringBuffer();
        for (TestSubjectVo testSubjectVo : testSubjects) {
            // 区分题目答案
            StringBuffer sb = new StringBuffer();
            StringBuffer scores = new StringBuffer();
            testSubjectVo.getTextList().stream().forEach((item) -> sb.append(item + "|"));
            testSubjectVo.getScoreList().stream().forEach((score) -> scores.append(score+","));
            if (sb.toString().endsWith("|")) {
                sb.delete(sb.length() - 1, sb.length());
            }
            if(scores.toString().endsWith(",")) {
                scores.delete(scores.length()-1,scores.length());
            }
            TestSubject testSubject = new TestSubject();
            testSubject.setAnswer(sb.toString());
            testSubject.setTitle(testSubjectVo.getTitle());
            testSubject.setType(testSubjectVo.getType());
            testSubject.setScore(scores.toString());
            testSubjectMapper.insert(testSubject);

            Long id = testSubjectMapper.selectLatestSubjectId();
            itemId.append(id + ",");
        }
        if (itemId.toString().endsWith(",")) {
            itemId.delete(itemId.length() - 1, itemId.length());
        }
        //打分规则
        StringBuilder scoreIds = new StringBuilder();
        List<TestScoreRulesVo> resultList = testVo.getResultList();
        for(TestScoreRulesVo testScoreRulesVo: resultList){
            StringBuffer scoreStr = new StringBuffer();
            testScoreRulesVo.getResultScore().stream().forEach((item) -> scoreStr.append(item+","));
            TestScoreRule testScoreRule = new TestScoreRule();
            testScoreRule.setResult(testScoreRulesVo.getResult());
            if(scoreStr.toString().endsWith(",")){
                scoreStr.delete(scoreStr.length()-1,scoreStr.length());
            }
            testScoreRule.setSegment(scoreStr.toString());
            testScoreRuleMapper.insert(testScoreRule);
            Long id = testScoreRuleMapper.selectLatestScoreRulesId();
            scoreIds.append(id+",");
        }
        if(scoreIds.toString().endsWith(",")){
            scoreIds.delete(scoreIds.length()-1,scoreIds.length());
        }

        Test test = new Test();
        test.setDate(testVo.getDate());
        test.setIsDel(DelEnum.IS_NOT_DEL.getValue());
        test.setItemIds(itemId.toString());
        test.setName(testVo.getName());
        test.setCover(testVo.getCover());
        test.setRulesId(scoreIds.toString());
        testMapper.insert(test);
    }

    @Override
    public List<Test> findTestList(String name) {
        List<Test> list = testMapper.selectTestListByName(name);
        return list;
    }

    @Override
    public List<TestSubjectVo> findTestItemList(Long id) {
        Test test = testMapper.selectByPrimaryKey(id);
        String[] split = test.getItemIds().split(",");

        List<TestSubjectVo> list = new ArrayList<>();
        for (String _id : split) {
            TestSubject testSubject = testSubjectMapper.selectByPrimaryKey(Long.parseLong(_id));
            TestSubjectVo testSubjectVo = new TestSubjectVo();
            testSubjectVo.setTitle(testSubject.getTitle());
            testSubjectVo.setType(testSubject.getType());
            testSubjectVo.setId(testSubject.getId());
            String[] split1 = testSubject.getAnswer().split("\\|");
            String[] split2 = testSubject.getScore().split(",");
            List<String> textList = new ArrayList<>();
            List<Integer> scoreList = new ArrayList<>();
            for (String answer : split1) {
                textList.add(answer);
            }
            for(String score : split2){
                scoreList.add(Integer.parseInt(score));
            }
            testSubjectVo.setTextList(textList);
            testSubjectVo.setScoreList(scoreList);
            list.add(testSubjectVo);
        }
        return list;
    }

    @Override
    public Test findTestById(Long id) {
        return testMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delTestPaper(Long testId) {
        Test test = new Test();
        test.setId(testId);
        test.setIsDel(DelEnum.IS_DEL.getValue());
        testMapper.updateByPrimaryKeySelective(test);
    }
}
