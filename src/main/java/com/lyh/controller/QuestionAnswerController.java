package com.lyh.controller;

import com.github.pagehelper.PageInfo;
import com.lyh.entity.QaReply;
import com.lyh.entity.QuestionAnswer;
import com.lyh.entity.vo.QaReplyVo;
import com.lyh.entity.vo.QaVo;
import com.lyh.entity.vo.MyQa;
import com.lyh.entity.vo.QuestionAnswerVo;
import com.lyh.service.QaReplyService;
import com.lyh.service.QuestionAnswerService;
import com.lyh.utils.Result;
import com.lyh.utils.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lyh
 * @ClassName QuestionReplyController
 * @createTime 2022/4/27
 */
@RestController
@RequestMapping("qa")
public class QuestionAnswerController {

    @Resource
    private QuestionAnswerService questionAnswerService;
    @Resource
    private QaReplyService qaReplyService;

    /**
     * @return
     * @Author lyh
     * @Description 回复问答
     * @Param
     * @Date 2022/4/27
     **/
    @PostMapping("addQuestionAnswer")
    public Result<Boolean> addQuestionAnswer(@RequestBody QuestionAnswer questionAnswer) {
        boolean flag = questionAnswerService.addReply(questionAnswer);
        return ResultUtil.ok(flag);
    }

    /**
    * @return
    * @Author lyh
    * @Description 删除问答
    * @Param
    * @Date 2022/5/4
    **/
    @GetMapping("delQuestionAnswer")
    public Result<?> delQuestionAnswer(Long qaId){
        questionAnswerService.delQuestionAnswer(qaId);
        return ResultUtil.ok();
    }

    /**
     * @return
     * @Author lyh
     * @Description 查询所有回答
     * @Param
     * @Date 2022/4/27
     **/
    @GetMapping("findQuestionAnswerList")
    public Result<List<QaVo>> findQuestionAnswerList(Long questionId, Long userId) {
        List<QaVo> list = questionAnswerService.findQuestionAnswerList(questionId, userId);
        return ResultUtil.ok(list);
    }

    /**
     * @return qaId 回答id   userId 用户id
     * @Author lyh
     * @Description 用户点击有用
     * @Param
     * @Date 2022/4/27
     **/
    @GetMapping("useful")
    public Result<Void> userThinkIsUseful(Long qaId, Long userId) {
        questionAnswerService.useful(qaId, userId);
        return ResultUtil.ok();
    }

    /**
     * @return qaId 回答id   userId 用户id
     * @Author lyh
     * @Description 用户取消点击有用
     * @Param
     * @Date 2022/4/27
     **/
    @GetMapping("cancelUseful")
    public Result<Void> userThinkIsNotUseful(Long qaId, Long userId) {
        questionAnswerService.cancelUseful(qaId, userId);
        return ResultUtil.ok();
    }

    /**
     * @return
     * @Author lyh
     * @Description 关注
     * @Param
     * @Date 2022/4/28
     **/
    @GetMapping("focus")
    public Result<Boolean> focus(Long answerUserId, Long loginUserId) {
        boolean flag = questionAnswerService.focus(answerUserId, loginUserId);
        return ResultUtil.ok(flag);
    }

    /**
     * @return
     * @Author lyh
     * @Description 取消关注
     * @Param
     * @Date 2022/4/28
     **/
    @GetMapping("unfocus")
    public Result<Boolean> unfocus(Long answerUserId, Long loginUserId) {
        boolean flag = questionAnswerService.unfocus(answerUserId, loginUserId);
        return ResultUtil.ok(flag);
    }

    /**
     * @return
     * @Author lyh
     * @Description 评论
     * @Param
     * @Date 2022/4/29
     **/
    @PostMapping("sendComment")
    public Result<QaReplyVo> sendComment(@RequestBody QaReply qaReply) {
        QaReplyVo qaReplyVo = qaReplyService.sendComment(qaReply);
        return ResultUtil.ok(qaReplyVo);
    }

    @GetMapping("findQaByUserId")
    public Result<List<MyQa>> findQaListByUserId(Long userId) {
        List<MyQa> list = questionAnswerService.findQaListByUserId(userId);
        return ResultUtil.ok(list);
    }

    /**
     * @return
     * @Author lyh
     * @Description 根据用户id查询用户的回答数
     * @Param
     * @Date 2022/5/1
     **/
    @GetMapping("userQaCount")
    public Result<Integer> userQaCount(Long userId) {
        Integer count = questionAnswerService.countQaByUserId(userId);
        return ResultUtil.ok(count);
    }


    /**
     * @return
     * @Author lyh
     * @Description 后台管理系统查询问答列表
     * @Param
     * @Date 2022/5/3
     **/
    @GetMapping("questionAnswerList")
    public Result<PageInfo<QuestionAnswerVo>> questionAnswerList(String username, String username1, String title,
                                                                 String startTime, String endTime,
                                                                 @RequestParam(defaultValue = "1") Integer pageIndex,
                                                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<QuestionAnswerVo> pageInfo =  questionAnswerService.findQuestionAnswerListInback(username,username1,title,startTime,endTime,pageIndex, pageSize);
        return ResultUtil.ok(pageInfo);
    }
}
