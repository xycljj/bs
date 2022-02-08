package com.lyh.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyh.entity.Admin;
import com.lyh.entity.ArticleType;
import com.lyh.service.AdministratorOperationInformationService;
import com.lyh.service.ArticleService;
import com.lyh.utils.Result;
import com.lyh.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: ArticleController
 * @Author: lyh
 * @Date: 2022/2/7
 */
@RestController
@Slf4j
@CrossOrigin
@RequestMapping("article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @Resource
    private AdministratorOperationInformationService administratorOperationInformationService;

    /**
     * @return
     * @Author lyh
     * @Description 添加文章类型
     * @Param articleType
     * @Date 2022/2/7
     **/
    @PostMapping("addArticleType")
    public Result<Boolean> addArticleType(@RequestBody ArticleType articleType) {
        articleType.setCreateTime(new Date());
        int i = articleService.addArticleType(articleType);
        if (i == 1) {
            return ResultUtil.ok(true);
        } else {
            return ResultUtil.fail("添加失败");
        }
    }

    /**
    * @return
    * @Author lyh
    * @Description 删除文章类型
    * @Param [id 文章类型id, adminId 管理员(操作员)id]
    * @Date 2022/2/8
    **/
    @GetMapping("delArticleType")
    public Result<Boolean> delArticleType(Long id,Long adminId){
        if(articleService.delArticleType(id) == 1){
            administratorOperationInformationService.addRecord(id,adminId);
            return ResultUtil.ok(true);
        }
        return ResultUtil.fail("删除失败");

    }


    /**
    * @return
    * @Author lyh
    * @Description 查询文章类型列表
    * @Param
    * @Date 2022/2/7
    **/
    @GetMapping("articleTypeList")
    public Result<PageInfo<ArticleType>> articleTypeList(@RequestParam(defaultValue = "1") Integer pageIndex,
                                                         @RequestParam(defaultValue = "10") Integer pageSize){
        Page<ArticleType> page = PageHelper.startPage(pageIndex,pageSize);
        List<ArticleType> articleTypeList = articleService.findArticleTypeList();
        PageInfo<ArticleType> pageInfo = page.toPageInfo();
        pageInfo.setList(articleTypeList);
        return ResultUtil.ok(pageInfo);
    }
}