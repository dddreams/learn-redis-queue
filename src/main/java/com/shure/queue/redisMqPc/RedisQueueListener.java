package com.shure.queue.redisMqPc;

import com.shure.queue.redisMqPc.consumer.Consumer;
import com.shure.queue.redisMqPc.consumer.ConsumerContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisQueueListener implements Runnable {

    public static final Logger log = LoggerFactory.getLogger(RedisQueueListener.class);

    private RedisTemplate<String, Object> redisTemplate;
    private String queue;
    private Consumer consumer;

    public RedisQueueListener(RedisTemplate<String, Object> redisTemplate, String queue, Consumer consumer) {
        this.redisTemplate = redisTemplate;
        this.queue = queue;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        log.info("RedisQueueListener 正在启动...queue:{}", queue);
        while (ConsumerContainer.run) {
            try {
                Object msg = redisTemplate.opsForList().rightPop(queue, 30, TimeUnit.SECONDS);
                if (msg != null) {
                    try {
                        consumer.onMessage(msg);
                    } catch (Exception e) {
                        consumer.onError(msg, e);
                    }
                }
            } catch (QueryTimeoutException ignored) {
            } catch (Exception e) {
                if (ConsumerContainer.run) {
                    log.error("异常 Queue:{}", queue, e);
                } else {
                    log.info("RedisQueueListener 退出...queue:{}", queue);
                }
            }
        }
    }
}
