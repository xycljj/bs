package com.lyh.controller;

import com.lyh.entity.MsgInfo;
import com.lyh.entity.SessionList;
import com.lyh.entity.vo.MsgInfoVo;
import com.lyh.service.MsgInfoService;
import com.lyh.service.SessionListService;
import com.lyh.utils.Result;
import com.lyh.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("msg")
public class MsgInfoController {

    @Resource
    private MsgInfoService msgInfoService;

    @Resource
    private SessionListService sessionListService;

    // 消息列表
    @GetMapping("msgList")
    public Result<List<MsgInfoVo>> msgList(@RequestParam Long sessionId){
        SessionList sessionList = sessionListService.findSessionListById(sessionId);
        if(sessionList == null){
            return ResultUtil.ok();
        }
        Long fromUserId = sessionList.getUserId();
        Long toUserId = sessionList.getToUserId();
        List<MsgInfoVo> msgInfoVoList = msgInfoService.selectMsgList(fromUserId,toUserId);
        // 更新消息已读
        msgInfoService.msgRead(fromUserId, toUserId);
        // 更新会话里面的未读消息
        sessionListService.delUnReadCount(fromUserId, toUserId);
        return ResultUtil.ok(msgInfoVoList);
    }


}