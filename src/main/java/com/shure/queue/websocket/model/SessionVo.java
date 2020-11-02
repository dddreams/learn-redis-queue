package com.shure.queue.websocket.model;

import lombok.Data;

import javax.websocket.Session;

@Data
public class SessionVo {
    private Session session;
    private String type;

    public SessionVo() {
    }

    public SessionVo(Session session, String type) {
        this.session = session;
        this.type = type;
    }
}
