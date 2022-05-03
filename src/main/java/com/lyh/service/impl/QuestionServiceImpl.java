package com.lyh.service.impl;

import com.github.pagehelper.PageHelper;
import com.lyh.dao.QuestionAnswerMapper;
import com.lyh.dao.QuestionMapper;
import com.lyh.dao.SubTagMapper;
import com.lyh.dao.UserMapper;
import com.lyh.entity.Question;
import com.lyh.entity.QuestionAnswer;
import com.lyh.entity.SubTag;
import com.lyh.entity.vo.QaVo;
import com.lyh.entity.vo.QuestionVo;
import com.lyh.enums.DelEnum;
import com.lyh.service.QuestionService;
import com.lyh.utils.RedisUtil;
import com.lyh.utils.Result;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lyh
 * @ClassName QuestionServiceImpl
 * @createTime 2022/4/26
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private SubTagMapper subTagMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private QuestionAnswerMapper questionAnswerMapper;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean addQuestion(Question question) {
        question.setCreateTime(new Date());
        question.setIsDel(DelEnum.IS_NOT_DEL.getValue());
        return questionMapper.insert(question) == 1;
    }

    @Override
    public List<QuestionVo> findQuestionList(Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Question> list = questionMapper.selectQuestionList();

        List<QuestionVo> questionVos = new ArrayList<>();
        for (Question question : list) {
            QuestionVo questionVo = new QuestionVo();
            questionVo.setQuestion(question);
            List<SubTag> subTags = subTagMapper.selectByIds(question.getSubTagIds());
            questionVo.setSubTagList(subTags);
            //...还没结束
            //回答数
            Example example = new Example(QuestionAnswer.class);
            example.createCriteria().andEqualTo("questionId", question.getId());
            int answerCount = questionAnswerMapper.selectCountByExample(example);
            questionVo.setAnswerCount(answerCount);

            // 有用数
            List<Long> qaIdList = questionAnswerMapper.selectIdList(question.getId());//所有回答id列表
            Long usefulCount = 0l;
            Long qaId = null;
            Long _count = -1l;
            for (Long id : qaIdList) {
                long count = redisUtil.sGetSetSize("qa" + id + "usefulList");
                long max = Math.max(_count, count);
                if (_count < max) {
                    qaId = id;
                    _count = max;
                }
                usefulCount += count;
            }
            if (qaId != null) {
                QuestionAnswer questionAnswer = questionAnswerMapper.selectByPrimaryKey(qaId);
                QaVo qaVo = new QaVo();
                qaVo.setQuestionAnswer(questionAnswer);
                qaVo.setAvatar(userMapper.selectByPrimaryKey(questionAnswer.getFromUserId()).getAvatar());
                questionVo.setQaVo(qaVo);
            }
            questionVo.setUsefulCount(usefulCount);
            questionVos.add(questionVo);
        }
        return questionVos;
    }

    @Override
    public QuestionVo findQuestionById(Long id, Long userId) {
        Question question = questionMapper.selectByPrimaryKey(id);
        QuestionVo questionVo = new QuestionVo();
        questionVo.setQuestion(question);
        // 标签2
        List<SubTag> subTags = subTagMapper.selectByIds(question.getSubTagIds());
        questionVo.setSubTagList(subTags);
        //问题提出者头像
        questionVo.setQuestionerAvatar(userMapper.selectByPrimaryKey(question.getUserId()).getAvatar());
        //回答数
        Example example = new Example(QuestionAnswer.class);
        example.createCriteria().andEqualTo("questionId", id);
        int answerCount = questionAnswerMapper.selectCountByExample(example);
        questionVo.setAnswerCount(answerCount);
        //抱抱数
        questionVo.setHug(redisUtil.sGetSetSize("question" + id + "hugList"));
        //收藏数
        questionVo.setCollected(redisUtil.sGetSetSize("question" + id + "collectedList"));
        if (userId == null) {
            questionVo.setIsHug(false);
            questionVo.setIsCollected(false);
        } else {
            questionVo.setIsHug(redisUtil.sHasKey("question" + id + "hugList", userId));
            questionVo.setIsCollected(redisUtil.sHasKey("question" + id + "collectedList", userId));
        }
        // 阅读数
        Object o = redisUtil.get("question" + question.getId() + "readCount");
        questionVo.setReadCount(Long.parseLong(String.valueOf(o)));
        return questionVo;
    }

    @Override
    public void giveAHug(Long questionId, Long userId) {
        redisUtil.sAdd("question" + questionId + "hugList", userId);
    }

    @Override
    public void collect(Long questionId, Long userId) {
        redisUtil.sAdd("question" + questionId + "collectedList", userId);
        redisUtil.incr("user:collection:count"+userId,1);
    }

    @Override
    public void cancelCollect(Long questionId, Long userId) {
        redisUtil.srem("question" + questionId + "collectedList", userId);
        redisUtil.decr("user:collection:count"+userId,1);
    }

    @Override
    public void addReadCount(Long id) {
        redisUtil.incr("question"+id+"readCount",1);
    }

    @Override
    public Integer collectionCountByUserId(Long userId) {
//        redisUtil.sGetSetSize()
        return null;
    }
}
