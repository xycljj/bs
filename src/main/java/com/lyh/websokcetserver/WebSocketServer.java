package com.lyh.websokcetserver;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lyh.dao.MsgInfoMapper;
import com.lyh.dao.SessionListMapper;
import com.lyh.dao.UserMapper;
import com.lyh.entity.MsgInfo;
import com.lyh.entity.SessionList;
import com.lyh.entity.User;
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
        if (userMapper == null){
            this.userMapper = (UserMapper)SpringContextUtil.getBean("userMapper");
        }
        User user = userMapper.selectByPrimaryKey(userId);
        CurPool.curUserPool.remove(user.getId());
       log.info("【websocket消息】连接断开，总数为:"+CurPool.webSockets.size());
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
        User user = userMapper.selectByPrimaryKey(sessionList.getUserId());
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
        // 判断用户是否存在，不存在就结束
        List<Object> list = CurPool.sessionPool.get(sessionList.getToUserId());
        if (list == null || list.isEmpty()) {
            // 用户不存在，更新未读数
            sessionListMapper.addUnReadCount(sessionList.getToUserId(), sessionList.getUserId());
        } else {
            // 用户存在，判断会话是否存在
            String id = sessionListMapper.selectIdByUser(sessionList.getToUserId(), sessionList.getUserId()) + "";
            String o = list.get(0) + "";
            if (id.equals(o)) {
                // 会话存在直接发送消息
                sendTextMessage(sessionList.getToUserId(), JsonUtils.objectToJson(msgInfo));
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
                    sessionListMapper.addUnReadCount(sessionList.getToUserId(), sessionList.getUserId());
                }
                // 会话不存在发送列表消息
                List<SessionListVo> sessionLists = sessionListMapper.selectByUserId(sessionList.getToUserId());
                sendTextMessage(sessionList.getToUserId(), JsonUtils.objectToJson(sessionLists));
            }
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
        Session session = (Session)CurPool.sessionPool.get(userId).get(1);
        if (session != null) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
