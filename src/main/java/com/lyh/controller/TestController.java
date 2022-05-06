package com.lyh.controller;

import com.lyh.entity.Test;
import com.lyh.entity.TestScoreRule;
import com.lyh.entity.vo.PaperDetail;
import com.lyh.entity.vo.TestScoreRulesVo;
import com.lyh.entity.vo.TestSubjectVo;
import com.lyh.entity.vo.TestVo;
import com.lyh.service.TestScoreRuleService;
import com.lyh.service.TestService;
import com.lyh.utils.Result;
import com.lyh.utils.ResultUtil;
import com.lyh.utils.UploadUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author lyh
 * @ClassName TestController
 * @createTime 2022/5/4
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Resource
    private TestService testService;

    @Resource
    private TestScoreRuleService testScoreRuleService;

    /**
    * @return
    * @Author lyh
    * @Description 添加测试
    * @Param
    * @Date 2022/5/4
    **/
    @PostMapping("addTest")
    public Result<?> addTest(@RequestBody TestVo testVo){
        testService.addTest(testVo);
        return ResultUtil.ok();
    }

    /**
    * @return
    * @Author lyh
    * @Description 删除测试卷
    * @Param
    * @Date 2022/5/4
    **/
    @GetMapping("delTestPaper")
    public Result<?> delTestPaper(Long testId){
        testService.delTestPaper(testId);
        return ResultUtil.ok();
    }

    /**
    * @return
    * @Author lyh
    * @Description 查询所有测试
    * @Param
    * @Date 2022/5/4
    **/
    @GetMapping("findTestList")
    public Result<List<Test> > findTestList(String name) {
        List<Test> list = testService.findTestList(name);
        return ResultUtil.ok(list);
    }

    /**
    * @return
    * @Author lyh
    * @Description 上传图片
    * @Param
    * @Date 2022/5/4
    **/
    @PostMapping("uploadCover")
    public Result<String> uploadCover(@RequestParam("file") MultipartFile file) {
        String url = null;
        try {
            url = UploadUtils.uploadFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtil.ok("上传成功",url);
    }

    /**
    * @return
    * @Author lyh
    * @Description 查询试卷详情
    * @Param    id 试卷主键id
    * @Date 2022/5/4
    **/
    @GetMapping("testSubject")
    public Result<?> testSubject(Long id){
        List<TestSubjectVo> testSubjects = testService.findTestItemList(id);
        PaperDetail paperDetail = new PaperDetail();
        paperDetail.setTextList(testSubjects);
        Test test = testService.findTestById(id);
        List<TestScoreRulesVo> resultList = testScoreRuleService.findListByIds(test.getRulesId());
        paperDetail.setPaperName(test.getName());
        paperDetail.setResultList(resultList);
        return ResultUtil.ok(paperDetail);
    }
}
