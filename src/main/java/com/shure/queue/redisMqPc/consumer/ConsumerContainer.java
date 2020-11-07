package com.shure.queue.redisMqPc.consumer;

import com.shure.queue.redisMqPc.RedisQueue;
import com.shure.queue.redisMqPc.RedisQueueListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ConsumerContainer {

    private static final Logger log = LoggerFactory.getLogger(ConsumerContainer.class);
    private RedisTemplate<String, Object> redisTemplate;

    private Map<String, RedisQueue> consumerMap = new HashMap<>();
    public static boolean run;
    private ExecutorService exec;

    public ConsumerContainer(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addConsumer(RedisQueue configuration) {
        if (consumerMap.containsKey(configuration.getQueue())) {
            log.warn("Key:{} 已经存在，", configuration.getQueue());
        }
        if (configuration.getConsumer() == null) {
            log.warn("Key:{} consumer 为空，无法对其进行配置", configuration.getQueue());
        }
        consumerMap.put(configuration.getQueue(), configuration);
    }

    public void destroy() {
        run = false;
        this.exec.shutdown();
        log.info("RedisQueueListener 正在退出...");
        while (!this.exec.isTerminated()) {

        }
        log.info("RedisQueueListener 退出");
    }

    public void init() {
        run = true;
        this.exec = Executors.newCachedThreadPool(r -> {
            final AtomicInteger threadNumber = new AtomicInteger(1);
            return new Thread(r, "RedisMQListener-" + threadNumber.getAndIncrement());
        });
        consumerMap = Collections.unmodifiableMap(consumerMap);
        consumerMap.forEach((k, v) -> exec.submit(new RedisQueueListener(redisTemplate, v.getQueue(), v.getConsumer())));
    }

}
