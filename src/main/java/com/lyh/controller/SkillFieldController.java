package com.lyh.controller;

import com.lyh.entity.SkillField;
import com.lyh.service.SkillFieldService;
import com.lyh.utils.Result;
import com.lyh.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lyh
 * @ClassName SkillFieldController
 * @createTime 2022/5/7
 */
@RestController
@RequestMapping("skill")
public class SkillFieldController {

    @Resource
    private SkillFieldService skillFieldService;


    @GetMapping("getField")
    public Result<?> getSkillList(){
        List<SkillField> list = skillFieldService.getList();
        return ResultUtil.ok(list);
    }
}
