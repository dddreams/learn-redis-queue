package com.shure.queue.redisMqPc.producer;

import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Producer(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void sendMsg(String queue, Object message) {
        String josnMessage = JSONUtil.parseObj(message, false).toString();
        redisTemplate.opsForList().leftPush(queue, josnMessage);
    }

}
