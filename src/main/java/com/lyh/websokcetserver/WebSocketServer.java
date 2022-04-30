package com.lyh.websokcetserver;

import com.lyh.dao.MsgInfoMapper;
import com.lyh.dao.SessionListMapper;
import com.lyh.dao.UserMapper;
import com.lyh.entity.MsgInfo;
import com.lyh.entity.SessionList;
import com.lyh.entity.User;
import com.lyh.entity.vo.MsgInfoVo;
import com.lyh.entity.vo.SessionListVo;
import com.lyh.utils.CurPool;
import com.lyh.utils.JsonUtils;
import com.lyh.utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lyh
 * @ClassName WebSocketServer
 * @createTime 2022/4/20
 */

/**
 * websocket服务
 */
@ServerEndpoint(value = "/websocket/{userId}/{sessionId}")
@Component
public class WebSocketServer {

    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    @Resource
    private SessionListMapper sessionListMapper;

    @Resource
    private MsgInfoMapper msgInfoMapper;

    @Resource
    private UserMapper userMapper;

    private Session session;


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") Long userId, @PathParam(value = "sessionId") Long sessionId) {
        this.session = session;
        CurPool.webSockets.put(userId, this);
        List<Object> list = new LinkedList<>();
        list.add(sessionId);
        list.add(session);
        CurPool.sessionPool.put(userId, list);
        log.info("【websocket消息】有新的连接，总数为:" + CurPool.webSockets.size());
    }

    @OnClose
    public void onClose() {
        // 断开连接删除用户删除session
        Long userId = Long.parseLong(this.session.getRequestParameterMap().get("userId").get(0));
        CurPool.sessionPool.remove(userId);
        CurPool.webSockets.remove(userId);
        if (userMapper == null) {
            this.userMapper = (UserMapper) SpringContextUtil.getBean("userMapper");
        }
        User user = userMapper.selectByPrimaryKey(userId);
        CurPool.curUserPool.remove(user.getId());
        log.info("【websocket消息】连接断开，总数为:" + CurPool.webSockets.size());
    }


    /**
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        String sessionId = this.session.getRequestParameterMap().get("sessionId").get(0);
        if (sessionId == null) {
            log.info("sessionId 错误");
        }

        // 在这里无法注入Mapper所以使用这种方式注入Mapper
        if (sessionListMapper == null) {
            this.sessionListMapper = (SessionListMapper) SpringContextUtil.getBean("sessionListMapper");
        }
        if (userMapper == null) {
            this.userMapper = (UserMapper) SpringContextUtil.getBean("userMapper");
        }
        if (msgInfoMapper == null) {
            this.msgInfoMapper = (MsgInfoMapper) SpringContextUtil.getBean("msgInfoMapper");
        }
        SessionList sessionList = sessionListMapper.selectByPrimaryKey(Long.parseLong(sessionId));
        // 为了获取username
        User user = userMapper.selectByPrimaryKey(sessionList.getUserId());
        // 封装消息
        MsgInfo msgInfo = new MsgInfo();
        msgInfo.setContent(message);
        msgInfo.setCreateTime(new Date());
        msgInfo.setFromUserId(sessionList.getUserId());
        msgInfo.setFromUsername(user.getUsername());
        msgInfo.setToUserId(sessionList.getToUserId());
        msgInfo.setToUsername(sessionList.getListName());
        msgInfo.setUnReadFlag(0);
        // 消息持久化
        msgInfoMapper.insertSelective(msgInfo);

        MsgInfoVo msgInfoVo = new MsgInfoVo();
        msgInfoVo.setContent(message);
        msgInfoVo.setCreateTime(new Date());
        msgInfoVo.setFromUserId(sessionList.getUserId());
        msgInfoVo.setFromUsername(user.getUsername());
        msgInfoVo.setToUserId(sessionList.getToUserId());
        msgInfoVo.setToUsername(sessionList.getListName());
        msgInfoVo.setFromAvatar(userMapper.selectByPrimaryKey(sessionList.getUserId()).getAvatar());
        msgInfoVo.setToAvatar(userMapper.selectByPrimaryKey(sessionList.getToUserId()).getAvatar());


        // 判断用户是否存在，不存在就结束
        List<Object> list = CurPool.sessionPool.get(sessionList.getToUserId());
        // 获得未读信息数量
        Integer count = sessionListMapper.countUnReadMsg(sessionList.getToUserId(), sessionList.getUserId());
        if (list == null || list.isEmpty()) {
            // 用户未有会话记录，更新未读数
            sessionListMapper.addUnReadCount(sessionList.getToUserId(), sessionList.getUserId(), count);
        } else {
            // 发送者和接收者的会话都存在
            // 接收者会话id
            String id = sessionListMapper.selectIdByUser(sessionList.getToUserId(), sessionList.getUserId()) + "";
            // list表示当前在线并连接着的接收者sessionId和session集合
            String o = list.get(0) + "";
            if (id.equals(o)) {
                // 双方通信建立,直接发消息并返回即可
                sendTextMessage(sessionList.getToUserId(), JsonUtils.objectToJson(msgInfoVo));
                // 双方都在该界面的时候默认已读
                msgInfoMapper.msgRead(sessionList.getToUserId(), sessionList.getUserId());
                log.info("【websocket消息】s收到客户端消息:" + msgInfo.getFromUsername() + " : " + message);
                return;
            } else {
                // 判断会话列表是否存在
                if (id == null || "".equals(id) || "null".equals(id)) {
                    // 新增会话列表
                    SessionList tmpSessionList = new SessionList();
                    tmpSessionList.setUserId(sessionList.getToUserId());
                    tmpSessionList.setToUserId(sessionList.getUserId());
                    tmpSessionList.setListName(user.getName());
                    tmpSessionList.setUnReadCount(1);
                    sessionListMapper.insert(tmpSessionList);
                } else {
                    // 更新未读消息数量
                    sessionListMapper.addUnReadCount(sessionList.getToUserId(), sessionList.getUserId(), count);
                }
                // 会话不存在发送列表消息
                List<SessionListVo> sessionLists = sessionListMapper.selectByUserId(sessionList.getToUserId());
                sendTextMessage(sessionList.getToUserId(), JsonUtils.objectToJson(sessionLists));
            }
            sessionListMapper.addUnReadCount(sessionList.getToUserId(), sessionList.getUserId(), count);
        }
        log.info("【websocket消息】s收到客户端消息:" + message);
    }


    /**
     * @return
     * @Author lyh
     * @Description 发送消息
     * @Param
     * @Date 2022/4/21
     **/
    public void sendTextMessage(Long userId, String message) {
        Session session = (Session) CurPool.sessionPool.get(userId).get(1);
        if (session != null) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
