package com.lyh.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyh.entity.Admin;
import com.lyh.entity.Article;
import com.lyh.entity.ArticleType;
import com.lyh.entity.User;
import com.lyh.entity.vo.ArticleVo;
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

    /**
    * @return boolean 是否成功存入数据库
    * @Author lyh
    * @Description 文章发布
    * @Param article 文章
    * @Date 2022/3/31
    **/
    @PostMapping("addArticle")
    public Result<Boolean> addArticle(@RequestBody Article article){
        boolean isAdd = articleService.addArticle(article);
        if(isAdd){
            return ResultUtil.ok(true);
        }
        log.info("文章添加失败");
        return ResultUtil.fail();
    }
    
    /**
    * @return
    * @Author lyh
    * @Description 查询所有文章
    * @Param 
    * @Date 2022/4/1
    **/
    @GetMapping("findArticleList")
    public Result<PageInfo<ArticleVo>> findArticleList(String title,
                                                    @RequestParam(defaultValue = "1") int pageIndex,
                                                    @RequestParam(defaultValue = "10") int pageSize){
        Page<ArticleVo> page = PageHelper.startPage(pageIndex, pageSize);
        List<ArticleVo> articleList = articleService.findArticleList(title);
        PageInfo<ArticleVo> pageInfo = page.toPageInfo();
        pageInfo.setList(articleList);
        return ResultUtil.ok(pageInfo);
    }
}
