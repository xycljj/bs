package com.lyh.controller;

import com.lyh.entity.SessionList;
import com.lyh.entity.User;
import com.lyh.entity.vo.SessionListVo;
import com.lyh.service.SessionListService;
import com.lyh.service.UserService;
import com.lyh.utils.Result;
import com.lyh.utils.ResultUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lyh
 * @ClassName SessionController
 * @createTime 2022/4/21
 */
@RestController
@RequestMapping("sessionList")
public class SessionController {

    @Resource
    private SessionListService sessionListService;

    @Resource
    private UserService userService;


    /**
     * @return
     * @Author lyh
     * @Description 获取已建立会话
     * @Param
     * @Date 2022/4/21
     **/
    @GetMapping("already")
    public Result<List<SessionListVo>> sessionListAlready(@RequestParam Long id) {
        List<SessionListVo> sessionLists = sessionListService.selectByUserId(id);
        return ResultUtil.ok(sessionLists);
    }

    /**
     * @return
     * @Author lyh
     * @Description 可建立会话的用户
     * @Param
     * @Date 2022/4/21
     **/
    @GetMapping("not")
    public Result<List<User>> sessionListNot(@RequestParam Long id) {
        List<Long> list = sessionListService.selectUserIdByUserId(id);
        list.add(id);
        List<User> cloudList = userService.getCloudList(list);
        return ResultUtil.ok(cloudList);
    }

    /**
     * @return
     * @Author lyh
     * @Description 创建会话
     * @Param
     * @Date 2022/4/21
     **/
    @GetMapping("createSession")
    public Result<?> createSession(@RequestParam Long id, @RequestParam Long toUserId, @RequestParam String toUserName) {
        // 先判断会话是否已经存在
        boolean isExist = sessionListService.selectListIsExist(id,toUserId);
        if(isExist){
            Long sessionId = sessionListService.selectSessionIdByUserIdAndToUserId(id,toUserId);
            return ResultUtil.ok("会话已经存在",sessionId);
        }
        SessionList sessionList = new SessionList();
        sessionList.setUserId(id);
        sessionList.setUnReadCount(0);
        sessionList.setListName(toUserName);
        sessionList.setToUserId(toUserId);
        sessionListService.createSession(sessionList);
        Long sessionId = sessionListService.selectSessionIdByUserIdAndToUserId(id,toUserId);
        // 判断对方和我建立会话没有？ 没有也要建立
        Long aLong = sessionListService.selectIdByUser(toUserId, id);
        if (aLong == null || aLong <= 0) {
            User user = userService.findUserById(id);
            sessionList.setUserId(toUserId);
            sessionList.setToUserId(id);
            sessionList.setListName(user.getUsername());
            sessionListService.createSession(sessionList);
        }
        return ResultUtil.ok(sessionId);
    }

    // 删除会话
    @Transactional
    @GetMapping("delSession")
    public Result<?> delSession(@RequestParam Long sessionId) {
        sessionListService.delSession(sessionId);
        return ResultUtil.ok();
    }
}
