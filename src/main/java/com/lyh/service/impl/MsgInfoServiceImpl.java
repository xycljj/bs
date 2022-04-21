package com.lyh.service.impl;

import com.lyh.dao.MsgInfoMapper;
import com.lyh.dao.UserMapper;
import com.lyh.entity.MsgInfo;
import com.lyh.entity.vo.MsgInfoVo;
import com.lyh.service.MsgInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lyh
 * @ClassName MsgInfoServiceImpl
 * @createTime 2022/4/21
 */
@Service
public class MsgInfoServiceImpl implements MsgInfoService {

    @Resource
    private MsgInfoMapper msgInfoMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public List<MsgInfoVo> selectMsgList(Long fromUserId, Long toUserId) {
        List<MsgInfoVo> msgInfoVoList = new ArrayList<>();
        List<MsgInfo> msgInfos = msgInfoMapper.selectMsgList(fromUserId, toUserId);
        for(MsgInfo msgInfo : msgInfos){
            MsgInfoVo msgInfoVo = new MsgInfoVo();
            msgInfoVo.setId(msgInfo.getId());
            msgInfoVo.setContent(msgInfo.getContent());
            msgInfoVo.setCreateTime(msgInfo.getCreateTime());
            msgInfoVo.setFromUserId(msgInfo.getFromUserId());
            msgInfoVo.setFromUsername(msgInfo.getFromUsername());
            msgInfoVo.setToUserId(msgInfo.getToUserId());
            msgInfoVo.setToUsername(msgInfo.getToUsername());
            msgInfoVo.setUnReadFlag(msgInfo.getUnReadFlag());
            msgInfoVo.setFromAvatar(userMapper.selectByPrimaryKey(msgInfo.getFromUserId()).getAvatar());
            msgInfoVo.setToAvatar(userMapper.selectByPrimaryKey(msgInfo.getToUserId()).getAvatar());
            msgInfoVoList.add(msgInfoVo);
        }
        return msgInfoVoList;
    }

    @Override
    public void msgRead(Long fromUserId, Long toUserId) {
        msgInfoMapper.msgRead(fromUserId, toUserId);
    }
}
