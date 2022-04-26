package com.lyh.dao;

import com.lyh.BaseMapper;
import com.lyh.entity.Question;

import java.util.List;

public interface QuestionMapper extends BaseMapper<Question> {

    List<Question> selectQuestionList();

}