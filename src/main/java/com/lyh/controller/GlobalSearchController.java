package com.lyh.controller;

import com.lyh.entity.Article;
import com.lyh.entity.User;
import com.lyh.entity.vo.ArticleVo;
import com.lyh.service.ArticleService;
import com.lyh.service.UserService;
import com.lyh.utils.RedisUtil;
import com.lyh.utils.Result;
import com.lyh.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author lyh
 * @ClassName GlobalSearchController
 * @createTime 2022/4/24
 */
@RestController
@RequestMapping("globalSearch")
public class GlobalSearchController {

    @Resource
    private UserService userService;

    @Resource
    private ArticleService articleService;

    @Resource
    private RedisUtil redisUtil;

    /**
    * @return
    * @Author lyh
    * @Description 全局搜索 可搜索用户 文章 问答
    * @Param
    * @Date 2022/4/24
    **/
    @GetMapping("search")
    public Result<List<List<Object>>> search(String searchStr){
        List<List<Object>> res = new ArrayList<>();
        //搜索用户
        List<User> users = userService.findUserBySearchStr(searchStr);
        // 搜索文章
        List<Article> articles = articleService.findArticleBySearchStr(searchStr);
        res.add(Collections.singletonList(users));
//        res.add(Collections.singletonList(articles));
        return ResultUtil.ok(res);
    }
}
