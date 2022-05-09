package com.lyh.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyh.entity.*;
import com.lyh.entity.vo.ArticleVo;
import com.lyh.entity.vo.ArticleVo1;
import com.lyh.enums.DelEnum;
import com.lyh.service.AdministratorOperationInformationService;
import com.lyh.service.ArticleService;
import com.lyh.utils.RedisUtil;
import com.lyh.utils.Result;
import com.lyh.utils.ResultUtil;
import com.lyh.utils.UploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    @Resource
    private RedisUtil redisUtil;


    /**
     * @return
     * @Author lyh
     * @Description 添加文章类型
     * @Param articleType
     * @Date 2022/2/7
     **/
    @PostMapping("addArticleType")
    public Result<ArticleType> addArticleType(@RequestBody ArticleType articleType) {
        articleType.setCreateTime(new Date());
        articleType.setIsDel(DelEnum.IS_NOT_DEL.getValue());
        int i = articleService.addArticleType(articleType);
        if (i == 1) {
            Long newArticleId = articleService.getNewArticleId(articleType.getName());
            articleType.setId(newArticleId);
            return ResultUtil.ok(articleType);
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
                                                         @RequestParam(defaultValue = "8") Integer pageSize){
        Page<ArticleType> page = PageHelper.startPage(pageIndex,pageSize);
        List<ArticleType> articleTypeList = articleService.findArticleTypeList();
        PageInfo<ArticleType> pageInfo = page.toPageInfo();
        pageInfo.setList(articleTypeList);
        return ResultUtil.ok(pageInfo);
    }

    /**
    * @return
    * @Author lyh
    * @Description 获取所有
    * @Param
    * @Date 2022/5/7
    **/
    @GetMapping("getTypeListAll")
    public Result<?> getArticleTypeListAll() {
        List<ArticleType> list = articleService.getArticleTypeListAll();
        return ResultUtil.ok(list);
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
    * @Description 删除文章
    * @Param
    * @Date 2022/5/3
    **/
    @GetMapping("delArticle")
    public Result<?> delArticle(Long articleId){
        boolean flag = articleService.delArticleById(articleId);
        return ResultUtil.ok(flag);
    }


    /**
     * @return
     * @Author lyh
     * @Description 编辑文章
     * @Param article 文章
     * @Date 2022/3/31
     **/
    @PostMapping("editArticle")
    public Result<Boolean> editArticle(@RequestBody Article article){
        boolean isAdd = articleService.editArticle(article);
        if(isAdd){
            return ResultUtil.ok(true);
        }
        log.info("文章编辑失败");
        return ResultUtil.fail("文章编辑失败");
    }

    /**
    * @return
    * @Author lyh
    * @Description 查询文章
    * @Param 
    * @Date 2022/4/1
    **/
    @GetMapping("findArticleList")
    public Result<PageInfo<ArticleVo>> findArticleList(String title, String username,
                                                    @RequestParam(defaultValue = "1") int pageIndex,
                                                    @RequestParam(defaultValue = "10") int pageSize){
        Page<ArticleVo> page = PageHelper.startPage(pageIndex, pageSize);
        List<ArticleVo> articleList = articleService.findArticleList(title,username);
        PageInfo<ArticleVo> pageInfo = page.toPageInfo();
        pageInfo.setList(articleList);
        return ResultUtil.ok(pageInfo);
    }

    /**
    * @return
    * @Author lyh
    * @Description 上传图片(封面/内容图片)
    * @Param
    * @Date 2022/4/19
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
    * @Description 修改上传封面
    * @Param
    * @Date 2022/4/12
    **/
    @PostMapping("cover")
    public Result<String> uploadFile(@RequestParam("file") MultipartFile file, Long articleId) {
        String url = null;
        try {
            url = UploadUtils.uploadFile(file);
            articleService.changeArticleCover(articleId,url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtil.ok("上传成功",url);
    }


    /**
    * @return
    * @Author lyh
    * @Description 根据用户id查询文章
    * @Param
    * @Date 2022/4/12
    **/
    @GetMapping("myArticles")
    public Result<List<ArticleVo>> getArticleByUserId(Long userId, @RequestParam(defaultValue = "1") Integer pageIndex,
                                                    @RequestParam(defaultValue = "10") Integer pageSize){
        PageHelper.startPage(pageIndex,pageSize);
        List<ArticleVo> articleList = articleService.getArticlesByUserId(userId);
        return ResultUtil.ok(articleList);
    }

    /**
    * @return
    * @Author lyh
    * @Description 根据文章id查询
    * @Param
    * @Date 2022/4/12
    **/
    @GetMapping("articleById")
    public Result<Article> getArticleById(Long articleId){
        Article article = articleService.getArticlesById(articleId);
        return ResultUtil.ok(article);
    }

    /**
    * @return
    * @Author lyh
    * @Description 根据文章类型id查询
    * @Param id 文章类型主键
    * @Date 2022/4/13
    **/
    @GetMapping("getArticleByTypeId")
    public Result<PageInfo<ArticleVo1>> getArticleByTypeId(Long id, @RequestParam(defaultValue = "1") Integer pageIndex,
                                                           @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<ArticleVo1> list = articleService.getArticleByTypeId(id,pageIndex,pageSize);
        return ResultUtil.ok(list);
    }

    /**
    * @return
    * @Author lyh
    * @Description  点赞
    * @Param
    * @Date 2022/4/15
    **/
    @GetMapping("doLike")
    public Result<Void> doLike(Long userId, Long articleId){
        redisUtil.sAdd("post:"+ articleId,userId);
        Long authorId = articleService.findAuthorByArticleId(articleId);
        redisUtil.incr("getCreditTo"+authorId,1);
        return ResultUtil.ok();
    }

    /**
    * @return
    * @Author lyh
    * @Description 取消点赞
    * @Param
    * @Date 2022/4/15
    **/
    @GetMapping("doUnLike")
    public Result<Void> doUnLike(Long userId, Long articleId){
        redisUtil.srem("post:"+ articleId,userId);
        Long authorId = articleService.findAuthorByArticleId(articleId);
        redisUtil.decr("getCreditTo"+authorId,1);
        return ResultUtil.ok();
    }

    /**
    * @return
    * @Author lyh
    * @Description 判断用户是否已经对该文章进行点赞
    * @Param
    * @Date 2022/4/15
    **/
    @GetMapping("userIsLike")
    public Result<Boolean> userIsLike(Long userId,Long articleId){
        boolean hasKey = redisUtil.sHasKey("post:" + articleId, userId);
        System.out.println(hasKey);
        return ResultUtil.ok(hasKey);
    }

    /**
    * @return
    * @Author lyh
    * @Description 文章点赞总数
    * @Param
    * @Date 2022/4/15
    **/
    @GetMapping("likeCount")
    public Result<Long> likeCount(Long articleId){
        long l = redisUtil.sGetSetSize("post:"+articleId);
        return ResultUtil.ok(l);
    }

    /**
    * @return
    * @Author lyh
    * @Description 收藏文章
    * @Param
    * @Date 2022/4/24
    **/
    @GetMapping("collection")
    public Result<Boolean> collection(Long userId, Long articleId){
        redisUtil.sAdd("ac"+userId,articleId);
        redisUtil.incr("collection:post:count"+articleId,1);
        redisUtil.incr("user:collection:count"+userId,1);
        return ResultUtil.ok(true);
    }

    /**
    * @return
    * @Author lyh
    * @Description 取消收藏文章
    * @Param
    * @Date 2022/4/24
    **/
    @GetMapping("cancelCollection")
    public Result<Boolean> cancelCollection(Long userId, Long articleId){
        redisUtil.srem("ac"+userId,articleId);
        redisUtil.decr("collection:post:count"+articleId,1);
        redisUtil.decr("user:collection:count"+userId,1);
        return ResultUtil.ok(true);
    }

    /**
    * @return
    * @Author lyh
    * @Description 用户是否已经收藏
    * @Param
    * @Date 2022/4/24
    **/
    @GetMapping("isUserCollected")
    public Result<Boolean> isUserCollected(Long userId, Long articleId){
        boolean isCollected = redisUtil.sHasKey("ac" + userId, articleId);
        return ResultUtil.ok(isCollected);
    }

    /**
    * @return
    * @Author lyh
    * @Description 增加阅读量
    * @Param
    * @Date 2022/4/24
    **/
    @GetMapping("increReadCount")
    public Result<Boolean> increaseReadCount(Long articleId){
        redisUtil.incr("post:read:count"+articleId,1);
        return ResultUtil.ok(true);
    }
}
