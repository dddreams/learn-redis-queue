package com.shure.queue.redisMqPc.consumer;

public interface Consumer {

    void onMessage(Object message);

    void onError(Object message, Exception e);

}
