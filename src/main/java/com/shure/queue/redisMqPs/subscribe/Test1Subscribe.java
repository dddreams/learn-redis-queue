package com.shure.queue.redisMqPs.subscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 测试订阅者
 */
@Service
public class Test1Subscribe implements Subscribe {

    private static final Logger logger = LoggerFactory.getLogger(Test1Subscribe.class);

    @Override
    public void receiveMessage(String message) {
        Thread th = Thread.currentThread();
        logger.info("log-thread:" + th.getName());
        logger.info("test1:" + message);
    }
}
