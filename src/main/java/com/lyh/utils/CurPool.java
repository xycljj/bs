package com.lyh.utils;

import com.lyh.entity.User;
import com.lyh.websokcetserver.WebSocketServer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CurPool {
    // 连接数
    public static Map<Long, WebSocketServer> webSockets = new ConcurrentHashMap<>();
    // list 里面第一个存sessionId，第二个存session
    public static Map<Long, List<Object>> sessionPool = new ConcurrentHashMap<>();
    // 当前登录用户x
    public static Map<Long, User> curUserPool = new ConcurrentHashMap<>();
}