package com.lyh.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyh.dao.ArticleMapper;
import com.lyh.dao.ReportMapper;
import com.lyh.dao.UserMapper;
import com.lyh.entity.Report;
import com.lyh.entity.vo.*;
import com.lyh.service.ReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author lyh
 * @ClassName ReportServiceImpl
 * @createTime 2022/5/2
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Resource
    private ReportMapper reportMapper;
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public void addReport(Report report) {
        report.setCreateTime(new Date());
        report.setType(0);
        reportMapper.insert(report);
    }

    @Override
    public PageInfo<ArticleReport> getArticleReportList(String username, String _username,
                                                        String startTime1, String endTime1,
                                                        String startTime2, String endTime2,
                                                        Integer pageIndex, Integer pageSize) {
        if (startTime1 != null && endTime1 != null) {
            startTime1 = startTime1 + " 00:00:00";
            endTime1 = endTime1 + " 23:59:59";
        }
        if (startTime2 != null && endTime2 != null) {
            startTime2 = startTime2 + " 00:00:00";
            endTime2 = endTime2 + " 23:59:59";
        }
        Page<ArticleReport> reportList = PageHelper.startPage(pageIndex, pageSize);
        List<ArticleReport> reports = reportMapper.selectList(username, _username, startTime1, endTime1, startTime2, endTime2);
        PageInfo<ArticleReport> reportPageInfo = reportList.toPageInfo();
        reportPageInfo.setList(reports);
        return reportPageInfo;
    }

    @Override
    public void treatment(Long id) {
        Report report = new Report();
        report.setId(id);
        report.setType(1);
        report.setProcessingTime(new Date());
        reportMapper.updateByPrimaryKeySelective(report);
    }

    @Override
    public PageInfo<ArticleCommentReport> getArticleCommentReportList(String username, String username1, String startTime1, String endTime1, String startTime2, String endTime2, Integer pageIndex, Integer pageSize) {
        if (startTime1 != null && endTime1 != null) {
            startTime1 = startTime1 + " 00:00:00";
            endTime1 = endTime1 + " 23:59:59";
        }
        if (startTime2 != null && endTime2 != null) {
            startTime2 = startTime2 + " 00:00:00";
            endTime2 = endTime2 + " 23:59:59";
        }
        Page<ArticleCommentReport> reportList = PageHelper.startPage(pageIndex, pageSize);
        List<ArticleCommentReport> reports = reportMapper.selectArticleCommentList(username, username1, startTime1, endTime1, startTime2, endTime2);
        PageInfo<ArticleCommentReport> reportPageInfo = reportList.toPageInfo();
        reportPageInfo.setList(reports);
        return reportPageInfo;
    }

    @Override
    public PageInfo<QuestionContentReport> getQuestionContentReportList(String username, String username1, String startTime1, String endTime1, String startTime2, String endTime2, Integer pageIndex, Integer pageSize) {
        if (startTime1 != null && endTime1 != null) {
            startTime1 = startTime1 + " 00:00:00";
            endTime1 = endTime1 + " 23:59:59";
        }
        if (startTime2 != null && endTime2 != null) {
            startTime2 = startTime2 + " 00:00:00";
            endTime2 = endTime2 + " 23:59:59";
        }
        Page<QuestionContentReport> reportList = PageHelper.startPage(pageIndex, pageSize);
        List<QuestionContentReport> reports = reportMapper.selectQuestionContentList(username, username1, startTime1, endTime1, startTime2, endTime2);
        PageInfo<QuestionContentReport> reportPageInfo = reportList.toPageInfo();
        reportPageInfo.setList(reports);
        return reportPageInfo;
    }

    @Override
    public PageInfo<QuestionAnswerReport> getQuestionAnswerReportList(String username, String username1, String startTime1, String endTime1, String startTime2, String endTime2, Integer pageIndex, Integer pageSize) {
        if (startTime1 != null && endTime1 != null) {
            startTime1 = startTime1 + " 00:00:00";
            endTime1 = endTime1 + " 23:59:59";
        }
        if (startTime2 != null && endTime2 != null) {
            startTime2 = startTime2 + " 00:00:00";
            endTime2 = endTime2 + " 23:59:59";
        }
        Page<QuestionAnswerReport> reportList = PageHelper.startPage(pageIndex, pageSize);
        List<QuestionAnswerReport> reports = reportMapper.selectQuestionAnswerList(username, username1, startTime1, endTime1, startTime2, endTime2);
        PageInfo<QuestionAnswerReport> reportPageInfo = reportList.toPageInfo();
        reportPageInfo.setList(reports);
        return reportPageInfo;
    }

    @Override
    public PageInfo<QuestionAnswerReplyReport> getQuestionAnswerReplyReportList(String username, String username1, String startTime1, String endTime1, String startTime2, String endTime2, Integer pageIndex, Integer pageSize) {
        if (startTime1 != null && endTime1 != null) {
            startTime1 = startTime1 + " 00:00:00";
            endTime1 = endTime1 + " 23:59:59";
        }
        if (startTime2 != null && endTime2 != null) {
            startTime2 = startTime2 + " 00:00:00";
            endTime2 = endTime2 + " 23:59:59";
        }
        Page<QuestionAnswerReplyReport> reportList = PageHelper.startPage(pageIndex, pageSize);
        List<QuestionAnswerReplyReport> reports = reportMapper.selectQuestionAnswerReplyList(username, username1, startTime1, endTime1, startTime2, endTime2);
        PageInfo<QuestionAnswerReplyReport> reportPageInfo = reportList.toPageInfo();
        reportPageInfo.setList(reports);
        return reportPageInfo;
    }
}
