package com.lyh.controller;

import com.github.pagehelper.PageInfo;
import com.lyh.entity.Report;
import com.lyh.entity.vo.*;
import com.lyh.service.ReportService;
import com.lyh.utils.Result;
import com.lyh.utils.ResultUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author lyh
 * @ClassName ReportController
 * @createTime 2022/5/2
 */
@RestController
@RequestMapping("report")
public class ReportController {

    @Resource
    private ReportService reportService;

    /**
     * @return
     * @Author lyh
     * @Description 举报
     * @Param
     * @Date 2022/5/2
     **/
    @Transactional
    @PostMapping("addReport")
    public Result<?> addReport(@RequestBody Report report) {
        reportService.addReport(report);
        return ResultUtil.ok();
    }

    /**
    * @return
    * @Author lyh
    * @Description 文章内容举报列表
    * @Param
    * @Date 2022/5/3
    **/
    @GetMapping("getArticleReportList")
    public Result<PageInfo<ArticleReport>> getArticleReportList(String username, String _username,
                                                                       String startTime1, String endTime1,
                                                                       String startTime2, String endTime2,
                                                                       @RequestParam(defaultValue = "1") Integer pageIndex,
                                                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<ArticleReport> pageInfo =
                reportService.getArticleReportList(username, _username, startTime1, endTime1, startTime2, endTime2, pageIndex, pageSize);
        return ResultUtil.ok(pageInfo);
    }

    /**
     * @return
     * @Author lyh
     * @Description 文章评论举报列表
     * @Param
     * @Date 2022/5/3
     **/
    @GetMapping("getArticleCommentReportList")
    public Result<PageInfo<ArticleCommentReport>> getArticleCommentReportList(String username, String _username,
                                                                String startTime1, String endTime1,
                                                                String startTime2, String endTime2,
                                                                @RequestParam(defaultValue = "1") Integer pageIndex,
                                                                @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<ArticleCommentReport> pageInfo =
                reportService.getArticleCommentReportList(username, _username, startTime1, endTime1, startTime2, endTime2, pageIndex, pageSize);
        return ResultUtil.ok(pageInfo);
    }

    /**
     * @return
     * @Author lyh
     * @Description 问题内容举报列表
     * @Param
     * @Date 2022/5/3
     **/
    @GetMapping("getQuestionCommentReportList")
    public Result<PageInfo<QuestionContentReport>> getQuestionContentReportList(String username, String _username,
                                                                                String startTime1, String endTime1,
                                                                                String startTime2, String endTime2,
                                                                                @RequestParam(defaultValue = "1") Integer pageIndex,
                                                                                @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<QuestionContentReport> pageInfo =
                reportService.getQuestionContentReportList(username, _username, startTime1, endTime1, startTime2, endTime2, pageIndex, pageSize);
        return ResultUtil.ok(pageInfo);
    }
    /**
     * @return
     * @Author lyh
     * @Description 问题回答举报列表
     * @Param
     * @Date 2022/5/3
     **/
    @GetMapping("getQuestionAnswerReportList")
    public Result<PageInfo<QuestionAnswerReport>> getQuestionAnswerReportList(String username, String _username,
                                                                              String startTime1, String endTime1,
                                                                              String startTime2, String endTime2,
                                                                              @RequestParam(defaultValue = "1") Integer pageIndex,
                                                                              @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<QuestionAnswerReport> pageInfo =
                reportService.getQuestionAnswerReportList(username, _username, startTime1, endTime1, startTime2, endTime2, pageIndex, pageSize);
        return ResultUtil.ok(pageInfo);
    }
    /**
     * @return
     * @Author lyh
     * @Description 问题回答举报列表
     * @Param
     * @Date 2022/5/3
     **/
    @GetMapping("getQuestionAnswerReplyReportList")
    public Result<PageInfo<QuestionAnswerReplyReport>> getQuestionAnswerReplyReportList(String username, String _username,
                                                                              String startTime1, String endTime1,
                                                                              String startTime2, String endTime2,
                                                                              @RequestParam(defaultValue = "1") Integer pageIndex,
                                                                              @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<QuestionAnswerReplyReport> pageInfo =
                reportService.getQuestionAnswerReplyReportList(username, _username, startTime1, endTime1, startTime2, endTime2, pageIndex, pageSize);
        return ResultUtil.ok(pageInfo);
    }
    /**
     * @return
     * @Author lyh
     * @Description 处理举报
     * @Param
     * @Date 2022/5/3
     **/
    @Transactional
    @GetMapping("treatment")
    public Result<?> treatment(Long id) {
        reportService.treatment(id);
        return ResultUtil.ok();
    }
}
