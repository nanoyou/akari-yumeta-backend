package com.github.nanoyou.akariyumetabackend.socketio;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 消息事件，作为后端与前台交
 */
@Component
public class MessageEventHandler {
    public static SocketIOServer socketIoServer;
    public static final Logger LOGGER = LoggerFactory.getLogger(MessageEventHandler.class);
    int id = 0;

    public static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    @Autowired
    public MessageEventHandler(SocketIOServer server) {
        this.socketIoServer = server;
    }

    /**
     * 客户端连接的时候触发，前端js触发：socket = io.connect("http://192.168.9.209:9092");
     *
     * @param client
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        LOGGER.info("客户端:" + client.getSessionId() + "已连接,id=" + (id++));
        executor.schedule(() -> {
            client.sendEvent("connectSuccess", "对喽");
        }, 500, TimeUnit.MILLISECONDS); // 延迟 500 毫秒执行
    }

    /**
     * 客户端关闭连接时触发：前端js触发：socket.disconnect();
     *
     * @param client
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        LOGGER.info("客户端:" + client.getSessionId() + "断开连接");
    }

    /**
     * @param client  　客户端信息
     * @param request 请求信息
     * @param data    　客户端发送数据{msgContent: msg}
     */
    @OnEvent(value = "test")
    public void test(SocketIOClient client, AckRequest request, String data) {
        LOGGER.info("发来消息：" + data);
    }

    /**
     * @param client  　客户端信息
     * @param request 请求信息
     * @param data    　客户端发送数据{msgContent: msg}
     */
    @OnEvent(value = "joinRoom")
    public void joinRoom(SocketIOClient client, AckRequest request, String data) {
        String roomId = data;
        client.joinRoom(roomId);
        LOGGER.info("加入房间：" + roomId);
    }

    /**
     * @param client  　客户端信息
     * @param request 请求信息
     * @param data    　客户端发送数据{msgContent: msg}
     */
    @OnEvent(value = "callRemote")
    public void callRemote(SocketIOClient client, AckRequest request, String data) {
        String roomId = data;
        executor.schedule(() -> {
            socketIoServer.getRoomOperations(roomId).sendEvent("callRemote");
        }, 500, TimeUnit.MILLISECONDS); // 延迟 500 毫秒执行

        LOGGER.info("callRemote已发送");
    }

    /**
     * @param client  　客户端信息
     * @param request 请求信息
     * @param data    　客户端发送数据{msgContent: msg}
     */
    @OnEvent(value = "acceptCall")
    public void acceptCall(SocketIOClient client, AckRequest request, String data) {
        String roomId = data;
        LOGGER.info("向A传递信息：" + roomId);
        executor.schedule(() -> {
            socketIoServer.getRoomOperations(roomId).sendEvent("acceptCall");
        }, 500, TimeUnit.MILLISECONDS); // 延迟 500 毫秒执行

    }

    /**
     * @param client  　客户端信息
     * @param request 请求信息
     * @param data    　客户端发送数据{msgContent: msg}
     */
    @OnEvent(value = "sendOffer")
    public void sendOffer(SocketIOClient client, AckRequest request, Map<String, Object> data) {
        String roomId = (String) data.get("room");
        Object offer = data.get("sdp");
        LOGGER.info("监听A的offer，来自房间：" + roomId + "   offer:" + offer);
        executor.schedule(() -> {
            socketIoServer.getRoomOperations(roomId).sendEvent("sendOffer", offer);
        }, 1000, TimeUnit.MILLISECONDS); // 延迟 500 毫秒执行

    }

    /**
     * @param client  　客户端信息
     * @param request 请求信息
     * @param data    　客户端发送数据{msgContent: msg}
     */
    @OnEvent(value = "sendAnswer")
    public void sendAnswer(SocketIOClient client, AckRequest request, Map<String, Object> data) {
        String roomId = (String) data.get("room");
        Object answer = data.get("sdp");
        LOGGER.info("监听B的answer，来自房间：" + roomId + "   answer:" + answer);
        executor.schedule(() -> {
            socketIoServer.getRoomOperations(roomId).sendEvent("sendAnswer", answer);
        }, 1500, TimeUnit.MILLISECONDS); // 延迟 500 毫秒执行


    }

    /**
     * @param client  　客户端信息
     * @param request 请求信息
     * @param data    　客户端发送数据{msgContent: msg}
     */
    @OnEvent(value = "sendCandidate")
    public void sendCandidate(SocketIOClient client, AckRequest request, Map<String, Object> data) {
        String roomId = (String) data.get("room");
        Object candidate = data.get("sdp");
        LOGGER.info("接收到 candidate，来自房间：" + roomId + "   candidate:" + candidate);
        executor.schedule(() -> {
            socketIoServer.getRoomOperations(roomId).sendEvent("sendCandidate", candidate);
        }, 1500, TimeUnit.MILLISECONDS); // 延迟 500 毫秒执行

    }

    /**
     * @param client  　客户端信息
     * @param request 请求信息
     * @param data    　客户端发送数据{msgContent: msg}
     */
    @OnEvent(value = "hangUp")
    public void hangUp(SocketIOClient client, AckRequest request, String data) {
        String roomId = data;
        LOGGER.info("收到连接断开请求，来自房间：" + roomId);
        socketIoServer.getRoomOperations(roomId).sendEvent("hangUp");
    }


}