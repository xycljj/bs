package com.lyh.dao;

import com.lyh.BaseMapper;
import com.lyh.entity.QuestionAnswer;
import com.lyh.entity.vo.MyQa;
import com.lyh.entity.vo.QuestionAnswerVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionAnswerMapper extends BaseMapper<QuestionAnswer> {

    List<Long> selectIdList(@Param("questionId") Long id);

    List<MyQa> selectMyQaList(@Param("userId") Long userId);

    List<QuestionAnswerVo> selectQuestionAnswerListInBack(@Param("username") String username, @Param("username1")String username1,
                                                          @Param("title") String title, @Param("startTime")String startTime,
                                                          @Param("endTime")String endTime);
}