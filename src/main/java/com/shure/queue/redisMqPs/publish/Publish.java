package com.shure.queue.redisMqPs.publish;

import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class Publish {

    private static final Logger logger = LoggerFactory.getLogger(Publish.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    //向通道发送消息的方法
    public void sendChannelMess(String channel, Object message) {
        String josnMessage = JSONUtil.parseObj(message, false).toString();
        redisTemplate.convertAndSend(channel, josnMessage);
    }
}
