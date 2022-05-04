package com.lyh.service;

import com.github.pagehelper.PageInfo;
import com.lyh.entity.QuestionAnswer;
import com.lyh.entity.vo.MyQa;
import com.lyh.entity.vo.QaVo;
import com.lyh.entity.vo.QuestionAnswerVo;

import java.util.List;

/**
 * @author lyh
 * @ClassName QuestionAnswerService
 * @createTime 2022/4/27
 */
public interface QuestionAnswerService {
    boolean addReply(QuestionAnswer questionAnswer);

    List<QaVo> findQuestionAnswerList(Long questionId, Long userId);

    void useful(Long qaId, Long userId);

    void cancelUseful(Long qaId, Long userId);

    boolean focus(Long answerUserId, Long loginUserId);

    boolean unfocus(Long answerUserId, Long loginUserId);

    List<MyQa> findQaListByUserId(Long userId);

    Integer countQaByUserId(Long userId);

    PageInfo<QuestionAnswerVo> findQuestionAnswerListInback(String username, String username1, String title, String startTime, String endTime, Integer pageIndex, Integer pageSize);

    void delQuestionAnswer(Long qaId);
}
