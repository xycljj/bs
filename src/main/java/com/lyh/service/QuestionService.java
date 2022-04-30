package com.lyh.service;

import com.lyh.entity.Question;
import com.lyh.entity.vo.QuestionVo;
import com.lyh.utils.Result;

import java.util.List;

/**
 * @author lyh
 * @ClassName QuestionService
 * @createTime 2022/4/26
 */
public interface QuestionService {
    boolean addQuestion(Question question);

    List<QuestionVo> findQuestionList(Integer pageIndex, Integer pageSize);

    QuestionVo findQuestionById(Long id, Long userId);

    void giveAHug(Long questionId, Long userId);

    void collect(Long questionId, Long userId);

    void cancelCollect(Long questionId, Long userId);

    void addReadCount(Long id);
}
