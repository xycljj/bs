package com.lyh.dao;

import com.lyh.BaseMapper;
import com.lyh.entity.Report;
import com.lyh.entity.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReportMapper extends BaseMapper<Report> {

    List<ArticleReport> selectList(@Param("username") String username, @Param("username1") String _username,
                                   @Param("startTime1") String startTime1, @Param("endTime1") String endTime1,
                                   @Param("startTime2") String startTime2, @Param("endTime2") String endTime2);

    List<ArticleCommentReport> selectArticleCommentList(@Param("username") String username, @Param("username1") String username1,
                                                        @Param("startTime1") String startTime1, @Param("endTime1") String endTime1,
                                                        @Param("startTime2") String startTime2, @Param("endTime2") String endTime2);

    List<QuestionContentReport> selectQuestionContentList(@Param("username") String username, @Param("username1") String username1,
                                                          @Param("startTime1") String startTime1, @Param("endTime1") String endTime1,
                                                          @Param("startTime2") String startTime2, @Param("endTime2") String endTime2);

    List<QuestionAnswerReport> selectQuestionAnswerList(@Param("username") String username, @Param("username1") String username1,
                                                        @Param("startTime1") String startTime1, @Param("endTime1") String endTime1,
                                                        @Param("startTime2") String startTime2, @Param("endTime2") String endTime2);

    List<QuestionAnswerReplyReport> selectQuestionAnswerReplyList(@Param("username") String username, @Param("username1") String username1,
                                                                  @Param("startTime1") String startTime1, @Param("endTime1") String endTime1,
                                                                  @Param("startTime2") String startTime2, @Param("endTime2") String endTime2);
}
