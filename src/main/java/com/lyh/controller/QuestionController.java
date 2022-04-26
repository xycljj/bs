package com.lyh.controller;

import com.lyh.entity.Question;
import com.lyh.entity.vo.QuestionVo;
import com.lyh.service.QuestionService;
import com.lyh.utils.Result;
import com.lyh.utils.ResultUtil;
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
    @PostMapping("addQuestion")
    public Result<Boolean> addQuestion(@RequestBody Question question){
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
    public Result<List<QuestionVo>> questionList(@RequestParam(defaultValue = "1") Integer pageIndex,
                                                 @RequestParam(defaultValue = "6") Integer pageSize){

        List<QuestionVo> list = questionService.findQuestionList(pageIndex,pageSize);
        return ResultUtil.ok(list);
    }

}
