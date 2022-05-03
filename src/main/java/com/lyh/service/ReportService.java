package com.lyh.service;

import com.github.pagehelper.PageInfo;
import com.lyh.entity.Report;
import com.lyh.entity.vo.*;

/**
 * @author lyh
 * @ClassName ReportService
 * @createTime 2022/5/2
 */
public interface ReportService {

    void addReport(Report report);

    PageInfo<ArticleReport> getArticleReportList(String username, String _username,
                                                 String startTime1, String endTime1,
                                                 String startTime2, String endTime2,
                                                 Integer pageIndex, Integer pageSize);

    void treatment(Long id);

    PageInfo<ArticleCommentReport> getArticleCommentReportList(String username, String username1, String startTime1, String endTime1, String startTime2, String endTime2, Integer pageIndex, Integer pageSize);

    PageInfo<QuestionContentReport> getQuestionContentReportList(String username, String username1, String startTime1, String endTime1, String startTime2, String endTime2, Integer pageIndex, Integer pageSize);

    PageInfo<QuestionAnswerReport> getQuestionAnswerReportList(String username, String username1, String startTime1, String endTime1, String startTime2, String endTime2, Integer pageIndex, Integer pageSize);

    PageInfo<QuestionAnswerReplyReport> getQuestionAnswerReplyReportList(String username, String username1, String startTime1, String endTime1, String startTime2, String endTime2, Integer pageIndex, Integer pageSize);
}
