package com.shure.queue.websocket.model;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * 消息Vo
 */
@Data
public class Message<T> implements Serializable {

    private String pkId;
    private String sessionId;
    private String userId;
    private String resId;
    private String tranId;
    private String msgType;
    private T msg;

    public Message() {
    }

    public Message(String pkid, String sessionId, String userId, String resId, String tranId, String msgType, T msg) {
        this.pkId = pkid;
        this.sessionId = sessionId;
        this.userId = userId;
        this.resId = resId;
        this.tranId = tranId;
        this.msgType = msgType;
        this.msg = msg;
    }

    public Message(String sessionId, String userId, String resId, String tranId, String msgType, T msg) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.resId = resId;
        this.tranId = tranId;
        this.msgType = msgType;
        this.msg = msg;
    }

    public Message(String sessionId, String msgType, T msg) {
        this.sessionId = sessionId;
        this.msgType = msgType;
        this.msg = msg;
    }

    public void setPkId() {
        this.pkId = UUID.randomUUID().toString();
    }

    public String getPkId() {
        return this.pkId;
    }

}
