package com.shure.queue.redisMqPs.subscribe;

public interface Subscribe {
    void receiveMessage(String message);
}
