package com.lyh.controller;

import com.github.pagehelper.PageInfo;
import com.lyh.entity.UserTestScore;
import com.lyh.entity.vo.UserTestScoreVo;
import com.lyh.service.UserTestScoreService;
import com.lyh.utils.Result;
import com.lyh.utils.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lyh
 * @ClassName UserTestScoreController
 * @createTime 2022/5/5
 */
@RestController
@RequestMapping("testScore")
public class UserTestScoreController {
    @Resource
    private UserTestScoreService userTestScoreService;


    /**
    * @return
    * @Author lyh
    * @Description 做测试
    * @Param
    * @Date 2022/5/5
    **/
    @PostMapping("doTest")
    public Result<?> doTest(@RequestBody UserTestScore userTestScore){
        userTestScoreService.doTest(userTestScore);
        return ResultUtil.ok();
    }


    /**
    * @return
    * @Author lyh
    * @Description 获取列表
    * @Param
    * @Date 2022/5/5
    **/
    @GetMapping("getTestScoreList")
    public Result<?> getTestScoreList(Long userId, Long testId,
                                      @RequestParam(defaultValue = "1") Integer pageIndex,
                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<UserTestScoreVo> pageInfo = userTestScoreService.findTestScoreListById(userId,testId,pageIndex,pageSize);
        return ResultUtil.ok(pageInfo);
    }
}
