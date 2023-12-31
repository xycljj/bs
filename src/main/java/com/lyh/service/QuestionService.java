package com.lyh.service;

import com.github.pagehelper.PageInfo;
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

    PageInfo<QuestionVo> findQuestionList(Integer pageIndex, Integer pageSize);

    QuestionVo findQuestionById(Long id, Long userId);

    void giveAHug(Long questionId, Long userId);

    void collect(Long questionId, Long userId);

    void cancelCollect(Long questionId, Long userId);

    void addReadCount(Long id);

    Integer collectionCountByUserId(Long userId);

    List<QuestionVo> collectionQuestion(Long userId);

    List<QuestionVo> myQuestion(Long userId);
}
