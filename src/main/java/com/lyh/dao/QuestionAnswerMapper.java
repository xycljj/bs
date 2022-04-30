package com.lyh.dao;

import com.lyh.BaseMapper;
import com.lyh.entity.QuestionAnswer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionAnswerMapper extends BaseMapper<QuestionAnswer> {

    List<Long> selectIdList(@Param("questionId") Long id);
}