package com.lyh.controller;

import com.lyh.entity.QaReply;
import com.lyh.entity.QuestionAnswer;
import com.lyh.entity.vo.QaReplyVo;
import com.lyh.entity.vo.QaVo;
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
    public Result<QaReplyVo> sendComment(@RequestBody QaReply qaReply){
        QaReplyVo qaReplyVo = qaReplyService.sendComment(qaReply);
        return ResultUtil.ok(qaReplyVo);
    }
}
