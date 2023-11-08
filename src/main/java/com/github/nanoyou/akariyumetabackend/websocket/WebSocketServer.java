package com.github.nanoyou.akariyumetabackend.websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

//import javax.websocket.*;
//import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * WebSocket服务类
 * @author Neo
 */
@Slf4j
@ServerEndpoint(value = "/ws/server")
@Component
public class WebSocketServer {

    //存储每一个客户端会话信息的线程安全的集合
    private static final CopyOnWriteArraySet<Session> sessions = new CopyOnWriteArraySet<>();
    //使用线程安全的计数器，记录在线数
    private static final AtomicInteger onlineCount = new AtomicInteger(0);

    /**
     * 连接成功时调用的方法
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        //存储会话信息
        sessions.add(session);
        //计数+1
        int cnt = onlineCount.incrementAndGet();
        //打印日志
        log.info("有连接加入，当前连接数为：" + cnt);
        //给客户端发消息
        this.sendMessage(session, "连接成功");
    }

    /**
     * 连接关闭时调用的方法
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        //删除会话信息
        sessions.remove(session);
        //计数-1
        int cnt = onlineCount.decrementAndGet();
        //打印日志
        log.info("有连接关闭，当前连接数为：" + cnt);
    }

    /**
     * 收到客户端消息时调用的方法
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        //打印日志
        log.info("来自客户端的消息：" + message);
        //给客户端发消息
        this.sendMessage(session, "收到消息，消息内容：" + message);
    }

    /**
     * 出现异常时调用的方法
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        //打印日志
        log.error("发生错误：Session ID：" + error.getMessage() + session.getId());
    }

    /**
     * 发送消息
     * @param session
     * @param message
     */
    public void sendMessage(Session session, String message) {
        try {
            //发送消息
            session.getBasicRemote().sendText("SID:::" + session.getId() + ":::" + message);
        } catch (IOException e) {
            //打印日志
            log.error("发送消息出错：" + e.getMessage());
        }
    }

    /**
     * 群发消息
     * 这个方法可以升级为消息推送的工具，给在线的客户端弹个广告啥的
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        for (Session session : sessions) {
            //判断连接是否开着
            if(session.isOpen()){
                //一个一个发
                this.sendMessage(session, message);
            }
        }
    }

    /**
     * 给指定的客户端发消息
     * 这个方法可以升级为即时通讯的工具，例如客户端A、客户端B、服务端WS
     * 首先，客户端要有身份id与WS的session进行绑定
     * 然后，A要给B发送消息，先获取B的身份id，然后将消息和B的身份id发给WS
     * WS收到A的消息后，拿着B的身份id去查询B与WS的会话信息，找到了，就把A的消息发送给B
     * @param sessionId
     * @param message
     * @throws IOException
     */
    public void sendMessage(String sessionId,String message) throws IOException {
        Session session = null;
        //遍历找会话信息
        for (Session s : sessions) {
            if(s.getId().equals(sessionId)){
                session = s;
                break;
            }
        }
        if(session!=null){
            //找到了，发消息
            this.sendMessage(session, message);
        } else{
            //打印日志
            log.warn("没有找到指定的会话：" + sessionId);
        }
    }
}