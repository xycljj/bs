package com.lyh.controller;

import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.lyh.entity.Admin;
import com.lyh.entity.User;
import com.lyh.entity.UserFocus;
import com.lyh.entity.vo.ArticleVo;
import com.lyh.entity.vo.CountVo;
import com.lyh.entity.vo.UserInfo;
import com.lyh.entity.vo.UserVo;
import com.lyh.exception.RrException;
import com.lyh.service.*;
import com.lyh.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author lyh
 * @ClassName UserController
 * @createTime 2021/12/16 10:55:00
 */
@RestController
@CrossOrigin
@Slf4j
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private ArticleService articleService;

    @Resource
    private QuestionService questionService;

    @Resource
    private UserFocusService userFocusService;

    @Resource
    private AdministratorOperationInformationService administratorOperationInformationService;

    @Resource
    private SessionListService sessionListService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private ApplicationFileService applicationFileService;

    @Resource
    private ApplicationService applicationService;

    @Resource
    private SkillFieldService skillFieldService;

    /**
     * @return
     * @Author lyh
     * @Description //TODO
     * @Param
     * @Date 2021/12/24
     **/
    @PostMapping("login")
    public Result<UserVo> login(@RequestBody User user) {
        User user1 = userService.login(user);
        if (user1 != null && redisUtil.sHasKey("userLoginList", user1.getId())) {
            return ResultUtil.fail("用户已经在别处登录");
        } else {
            if (user1 != null) {
                UserVo userVo = new UserVo();
                String token = TokenUtils.token(user1.getId(),"user");
                userVo.setUser(user1);
                userVo.setToken(token);
                redisUtil.sAdd("userLoginList", user1.getId());
                log.info("用户 " + user1.getUsername() + " 登录成功");
                CurPool.curUserPool.put(user1.getId(), user1);
                log.info("【websocket消息】连接建立，总数为:" + CurPool.webSockets.size());
                return ResultUtil.ok(userVo);
            }
            return ResultUtil.fail("用户名密码错误");
        }
    }

    /**
     * @return
     * @Author lyh
     * @Description //TODO 注册
     * @Param
     * @Date 2021/12/16
     **/
    @Transactional
    @PostMapping("addUser")
    public Result<User> addUser(@RequestBody User user) {
        if (userService.addUser(user) == 1) {
            return ResultUtil.ok(user);
        }
        return ResultUtil.fail("用户名已存在");
    }

    /**
     * @return
     * @Author lyh
     * @Description //TODO 删除
     * @Param [id 用户id,session ]
     * @Date 2021/12/17
     **/
    @GetMapping("delUser")
    public Result<Boolean> delUser(Long id, Long adminId) {
        if (userService.delUser(id) == 1) {
            //如果删除成功,则添加删除的记录
            administratorOperationInformationService.addRecord(id, adminId);
            return ResultUtil.ok(true);
        }
        return ResultUtil.fail("删除失败");
    }

    /**
     * @return
     * @Author lyh
     * @Description //TODO 编辑/更新
     * @Param
     * @Date 2021/12/17
     **/
    @Transactional
    @PostMapping("edit")
    public Result<User> editUser(@RequestBody User user, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        User userInfo = userService.editUser(user, admin);
        return ResultUtil.ok(userInfo);
    }

    /**
     * @return
     * @Author lyh
     * @Description //TODO 查询所有用户
     * @Param
     * @Date 2021/12/16
     **/
    @GetMapping("findUserList")
    public Result<PageInfo<User>> findUserList(String username, String phone,
                                               @RequestParam(defaultValue = "1") int pageIndex,
                                               @RequestParam(defaultValue = "10") int pageSize) {
        Page<User> page = PageHelper.startPage(pageIndex, pageSize);
        List<User> userList = userService.findUserList(username, phone);
        PageInfo<User> pageInfo = page.toPageInfo();
        pageInfo.setList(userList);
        return ResultUtil.ok(pageInfo);
    }

    /**
     * @return
     * @Author lyh
     * @Description //TODO 查个人详情
     * @Param
     * @Date 2021/12/16
     **/
    @GetMapping("findUserById")
    public Result<UserInfo> findUserById(Long id) {
        User user = userService.findUserById(id);
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user,userInfo);
        if(!StringUtils.isEmpty(user.getSkillField())){
            List<String> skills = skillFieldService.getListString(user.getSkillField());
            userInfo.setSkills(skills);
        }
        return ResultUtil.ok(userInfo);
    }

    /**
     * @return Result<Boolean> true 表示用户名已存在，false表示用户名未被注册
     * @Author lyh
     * @Description //TODO 查询用户名是否存在
     * @Param username 用户名
     * @Date 2021/12/17
     **/
    @GetMapping("findUserByUsername")
    public Result<Boolean> findUserByUsername(String username) {
        if (StringUtil.isEmpty(username)) {
            return ResultUtil.ok("未输入用户名", false);
        }
        User user = userService.findUserByUsername(username);
        if (user == null) {
            return ResultUtil.ok(false);
        }
        return ResultUtil.ok(true);
    }

    /**
     * @return
     * @Author lyh
     * @Description //TODO info 验证token
     * @Param
     * @Date 2021/12/28
     **/
    @GetMapping("info")
    public Result<UserVo> userInfo(String token) {
        UserVo userVo = new UserVo();
        userVo.setIsTrue(TokenUtils.verify(token));
        Long idFromToken = TokenUtils.getIdFromToken(token);
        if (!userVo.getIsTrue()) {
            redisUtil.srem("userLoginList", idFromToken);
            return ResultUtil.fail("离开页面太久，登录已失效....");
        }
        Long uid = TokenUtils.getIdFromToken(token);
        User user = userService.findUserById(uid);
        userVo.setUser(user);
        return ResultUtil.ok(userVo);
    }

    /**
     * @return
     * @Author lyh
     * @Description //TODO 退出登录
     * @Param
     * @Date 2021/12/29
     **/
    @Transactional
    @PostMapping("logout")
    public Result<String> logout(Long userId) {
        CurPool.curUserPool.remove(userId);
        CurPool.webSockets.remove(userId);
        CurPool.sessionPool.remove(userId);
        log.info("【websocket消息】连接断开，总数为:" + CurPool.webSockets.size());
        log.info("用户退出登录");
        redisUtil.srem("userLoginList", userId);
        return ResultUtil.ok("退出登录");
    }

    /**
    * @return
    * @Author lyh
    * @Description 注销
    * @Param
    * @Date 2022/5/13
    **/
    @Transactional
    @PostMapping("cancellation")
    public Result<?> cancellation(Long userId) {
        boolean flag = userService.cancellation(userId);
        if(flag){
            return ResultUtil.ok("注销成功");
        }
        return ResultUtil.fail("注销失败");
    }

    /**
     * @return
     * @Author lyh
     * @Description 上传图片(头像)
     * @Param
     * @Date 2022/4/5
     **/
    @Transactional
    @PostMapping("avator")
    public Result<String> uploadFile(@RequestParam("file") MultipartFile file, Long userId) {
        String url = null;
        try {
            url = UploadUtils.uploadFile(file);
            userService.changeUserInfo(userId, url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtil.ok("上传成功", url);
    }

    /**
     * @return
     * @Author lyh
     * @Description 获取该作者的所有文章总数
     * @Param id 作者id
     * @Date 2022/4/14
     **/
    @GetMapping("countAuthorsArticles")
    public Result<Integer> countAuthorsArticles(Long id) {
        Integer count = articleService.countAuthorsArticles(id);
        return ResultUtil.ok(count);
    }

    /**
     * @return
     * @Author lyh
     * @Description 添加关注
     * @Param
     * @Date 2022/4/14
     **/
    @Transactional
    @PostMapping("focus")
    public Result<Boolean> focus(@RequestBody UserFocus userFocus) {
        boolean flag = userFocusService.focus(userFocus);
        return ResultUtil.ok(flag);
    }

    /**
     * @return
     * @Author lyh
     * @Description 取消关注
     * @Param
     * @Date 2022/4/15
     **/
    @Transactional
    @PostMapping("cancelFocus")
    public Result<Boolean> cancelFocus(@RequestBody UserFocus userFocus) {
        boolean flag = userFocusService.cancelFocus(userFocus);
        return ResultUtil.ok(!flag);
    }

    /**
     * @return
     * @Author lyh
     * @Description 是否关注
     * @Param
     * @Date 2022/4/15
     **/
    @Transactional
    @PostMapping("isFocused")
    public Result<Boolean> isFocused(@RequestBody UserFocus userFocus) {
        boolean flag = userFocusService.isFocused(userFocus);
        return ResultUtil.ok(flag);
    }

    /**
     * @return
     * @Author lyh
     * @Description 查询我的关注列表
     * @Param
     * @Date 2022/4/22
     **/
    @GetMapping("myFocusUsers")
    public Result<List<User>> findMyFocusUsers(Long userId) {
        List<User> list = userService.findMyFocusUsers(userId);
        return ResultUtil.ok(list);
    }

    /**
     * @return
     * @Author lyh
     * @Description 未读消息
     * @Param
     * @Date 2022/4/24
     **/
    @GetMapping("unReadCount")
    public Result<Integer> countUnReadMsg(Long userId) {
        Integer count = sessionListService.findUnReadMsgCountByUserId(userId);
        return ResultUtil.ok(count);
    }

    /**
     * @return
     * @Author lyh
     * @Description 我的收藏
     * @Param
     * @Date 2022/4/30
     **/
    @GetMapping("myCollections")
    public Result<List<ArticleVo>> getMyCollections(Long userId) {
        //获取所有该用户关注的文章id
        Set<Object> articleIds = redisUtil.sGet("ac" + userId);
        StringBuffer sb = new StringBuffer();
        List<ArticleVo> list = new ArrayList<>();
        for (Object obj : articleIds) {
            sb.append(Long.parseLong(String.valueOf(obj)));
            sb.append(",");
        }
        if (sb.length() > 0) {
            sb.delete(sb.length() - 1, sb.length());
            list = articleService.findArticleByIds(sb.toString());
        }
        return ResultUtil.ok(list);
    }

    /**
     * @return
     * @Author lyh
     * @Description 上传成为咨询师的资质文件
     * @Param
     * @Date 2022/4/30
     **/
    @Transactional
    @PostMapping("toBeConsultant")
    public Result<Boolean> uploadQualification(@RequestParam("file") MultipartFile[] files, @RequestParam("userId") Long userId,
                                               @RequestParam("skillField") List<Long> skillField) {
        try {
            List<String> urlList = UploadUtils.uploadConsultantQualification(files);
            if (urlList.size() > 0) {
                StringBuffer sb = new StringBuffer();
                for (String url : urlList) {
                    Long id = applicationFileService.addFileUrl(url);
                    sb.append(id);
                    sb.append(",");
                }
                if (sb.toString().endsWith(",")) {
                    sb = sb.delete(sb.length() - 1, sb.length());
                }
                StringBuilder sb1 = new StringBuilder();
                for(Long _id : skillField){
                    sb1.append(_id+",");
                }
                if(sb1.toString().endsWith(",")){
                    sb1.delete(sb1.length()-1,sb1.length());
                }
                applicationService.addApplication(sb.toString(), userId,sb1.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtil.ok("上传成功", true);
    }

    /**
     * @return
     * @Author lyh
     * @Description 所有收藏数量(问答 + 文章)
     * @Param
     * @Date 2022/5/1
     **/
    @GetMapping("collectionCount")
    public Result<?> collectionCount(Long userId) {
        Integer count = userService.collectionCount(userId);
        return ResultUtil.ok(count);
    }

    /**
    * @return
    * @Author lyh
    * @Description 获赞(文章,评论,问答是否有用)
    * @Param
    * @Date 2022/5/2
    **/
    @GetMapping("getCreditToCount")
    public Result<?> getCreditToCount(Long userId) {
        Integer count = userService.getCreditToCount(userId);
        return ResultUtil.ok(count);
    }

    /**
    * @return
    * @Author lyh
    * @Description 查询心理咨询师
    * @Param
    * @Date 2022/5/11
    **/
    @GetMapping("getConsultantList")
    public Result<?> getConsultantList(String username, Long skillFieldId,
                                       @RequestParam(defaultValue = "1") Integer pageIndex,
                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<UserInfo> pageInfo = userService.getConsultantList(username,skillFieldId,pageIndex,pageSize);
        return ResultUtil.ok(pageInfo);
    }

    /**
    * @return
    * @Author lyh
    * @Description 获取4样数据(获赞数/收藏数/回答数)
    * @Param
    * @Date 2022/5/24
    **/
    @GetMapping("getReplyLikeCollectCounts")
    public Result<CountVo> getReplyLikeCollectCounts(Long userId){
        if(userId == null){
            return ResultUtil.ok("并没有用户id传入",new CountVo().setLikeCount(0).setCollectCount(0).setArticleCount(0).setReplyCount(0));
        }
        CountVo countVo = userService.findReplyLikeCollectCounts(userId);
        return ResultUtil.ok(countVo);
    }

}
