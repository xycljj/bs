package com.lyh.service.impl;

import com.github.pagehelper.PageHelper;
import com.lyh.dao.QuestionMapper;
import com.lyh.dao.SubTagMapper;
import com.lyh.entity.Question;
import com.lyh.entity.SubTag;
import com.lyh.entity.vo.QuestionVo;
import com.lyh.enums.DelEnum;
import com.lyh.service.QuestionService;
import org.springframework.stereotype.Service;

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

    @Override
    public boolean addQuestion(Question question) {
        question.setCreateTime(new Date());
        question.setIsDel(DelEnum.IS_NOT_DEL.getValue());
        return questionMapper.insert(question) == 1;
    }

    @Override
    public List<QuestionVo> findQuestionList(Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex,pageSize);
        List<Question> list = questionMapper.selectQuestionList();

        List<QuestionVo> questionVos = new ArrayList<>();
        for(Question question : list){
            QuestionVo questionVo = new QuestionVo();
            questionVo.setQuestion(question);
            List<SubTag> subTags = subTagMapper.selectByIds(question.getSubTagIds());
            questionVo.setSubTagList(subTags);
            //...还没结束
            questionVos.add(questionVo);
        }
        return questionVos;
    }
}
