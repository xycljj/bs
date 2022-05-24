package com.lyh.controller;

import com.github.pagehelper.PageInfo;
import com.lyh.entity.Question;
import com.lyh.entity.vo.QuestionVo;
import com.lyh.service.QuestionService;
import com.lyh.utils.RedisUtil;
import com.lyh.utils.Result;
import com.lyh.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lyh
 * @ClassName QuestionController
 * @createTime 2022/4/26
 */
@RestController
@RequestMapping("question")
public class QuestionController {

    @Resource
    private QuestionService questionService;


    /**
     * @return
     * @Author lyh
     * @Description 发布问题
     * @Param
     * @Date 2022/4/26
     **/
    @Transactional
    @PostMapping("addQuestion")
    public Result<Boolean> addQuestion(@RequestBody Question question) {
        boolean flag = questionService.addQuestion(question);
        return ResultUtil.ok(flag);
    }

    /**
     * @return
     * @Author lyh
     * @Description 首页问答列表
     * @Param
     * @Date 2022/4/26
     **/
    @GetMapping("getQuestionList")
    public Result<PageInfo<QuestionVo>> questionList(@RequestParam(defaultValue = "1") Integer pageIndex,
                                                     @RequestParam(defaultValue = "6") Integer pageSize) {

        PageInfo<QuestionVo> list = questionService.findQuestionList(pageIndex, pageSize);
        return ResultUtil.ok(list);
    }

    /**
     * @return
     * @Author lyh
     * @Description 根据id查询问答详情
     * @Param
     * @Date 2022/4/27
     **/
    @GetMapping("getQuestionById")
    public Result<QuestionVo> getQuestionById(Long id, Long userId) {
        QuestionVo questionVo = questionService.findQuestionById(id, userId);
        return ResultUtil.ok(questionVo);
    }

    /**
     * @return
     * @Author lyh
     * @Description 给个抱抱
     * @Param
     * @Date 2022/4/28
     **/
    @GetMapping("giveHug")
    public Result<Void> giveAHug(Long questionId, Long userId) {
        questionService.giveAHug(questionId, userId);
        return ResultUtil.ok();
    }

    /**
     * @return
     * @Author lyh
     * @Description 收藏该问答
     * @Param
     * @Date 2022/4/28
     **/
    @GetMapping("collect")
    public Result<Void> collect(Long questionId, Long userId) {
        questionService.collect(questionId, userId);
        return ResultUtil.ok();
    }

    /**
     * @return
     * @Author lyh
     * @Description 取消收藏问答
     * @Param
     * @Date 2022/4/28
     **/
    @GetMapping("cancelCollect")
    public Result<Void> cancelCollect(Long questionId, Long userId) {
        questionService.cancelCollect(questionId, userId);
        return ResultUtil.ok();
    }

    /**
    * @return
    * @Author lyh
    * @Description 阅读增加阅读量
    * @Param
    * @Date 2022/4/28
    **/
    @GetMapping("addReadCount")
    public Result<Void> addReadCount(Long id){
        questionService.addReadCount(id);
        return ResultUtil.ok();
    }


    /**
    * @return
    * @Author lyh
    * @Description 我的收藏中的问题
    * @Param
    * @Date 2022/5/13
    **/
    @GetMapping("collectionQuestion")
    public Result<?> collectionQuestion(Long userId) {
        List<QuestionVo> list = questionService.collectionQuestion(userId);
        return ResultUtil.ok(list);
    }

    /**
     * @return
     * @Author lyh
     * @Description 我的收藏中的问题
     * @Param
     * @Date 2022/5/13
     **/
    @GetMapping("myQuestion")
    public Result<?> myQuestion(Long userId) {
        List<QuestionVo> list = questionService.myQuestion(userId);
        return ResultUtil.ok(list);
    }
}



