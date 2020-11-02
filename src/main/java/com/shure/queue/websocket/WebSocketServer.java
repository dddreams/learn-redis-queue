package com.shure.queue.websocket;

import com.shure.queue.websocket.model.Message;
import com.shure.queue.websocket.model.SessionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

//@ServerEndpoint("/webSocket/{type}")
//@Component
public class WebSocketServer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineNum = new AtomicInteger();

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    private static ConcurrentHashMap<String, SessionVo> sessionPools = new ConcurrentHashMap<>();

    //发送消息
    public void sendMessage(Session session, Message message) throws IOException, EncodeException {
        if (session != null) {
            synchronized (session) {
                session.getBasicRemote().sendObject(message);
            }
        }
    }

    //给指定客户端发送信息
    public void sendInfo(String sessionId, Message message) {
        Session session = sessionPools.get(sessionId).getSession();
        try {
            sendMessage(session, message);
        } catch (Exception e) {
            logger.error("发送消息异常！");
            e.printStackTrace();
        }
    }

    //建立连接成功调用
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "type") String type) {
        sessionPools.put(session.getId(), new SessionVo(session, type));
        addOnlineCount();
        logger.info("客户端：" + session.getId() + "加入webSocket！当前客户端数为" + onlineNum);
        try {
            Message message = new Message(session.getId(), type, "建立连接成功！");
            sendMessage(session, message);
        } catch (Exception e) {
            logger.error("建立连接发生异常！");
            e.printStackTrace();
        }
    }

    //关闭连接时调用
    @OnClose
    public void onClose(Session session) {
        sessionPools.remove(session.getId());
        subOnlineCount();
        logger.info(session.getId() + "断开webSocket连接！当前客户端数为" + onlineNum);
    }

    //收到客户端信息
    @OnMessage
    public void onMessage(Session curSession, Message message) throws IOException {
        logger.info(message.toString());
        for (SessionVo vo : sessionPools.values()) {
            Session session = vo.getSession();
            try {
                if (curSession != session && message.getMsgType().equals(vo.getType())) {
                    sendMessage(session, message);
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.error("发生错误");
        throwable.printStackTrace();
    }

    public static void addOnlineCount() {
        onlineNum.incrementAndGet();
    }

    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }

}
