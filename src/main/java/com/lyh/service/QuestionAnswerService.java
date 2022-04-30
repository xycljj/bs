package com.lyh.service;

import com.lyh.entity.QuestionAnswer;
import com.lyh.entity.vo.QaVo;

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
}
