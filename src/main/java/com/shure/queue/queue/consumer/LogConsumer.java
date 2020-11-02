package com.shure.queue.queue.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 日志消费者
 */
@Service
public class LogConsumer implements Consumer {

    private static final Logger logger = LoggerFactory.getLogger(LogConsumer.class);

    @Override
    public void receiveMessage(String message) {
        Thread th = Thread.currentThread();
        logger.info("log-thread:" + th.getName());
        //MqMessage mmsg = JSONUtil.toBean(String.valueOf(message), MqMessage.class);
        logger.info("log:" + message);

        saveLog(message);
    }

    private void saveLog(String message) {

        logger.info("log:====保存");
    }
}
