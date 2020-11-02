package com.shure.queue.queue.producer;

import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Resource(name = "stringRedisTemplate")
    private RedisTemplate stringRedisTemplate;

    //向通道发送消息的方法
    public void sendChannelMess(String channel, Object message) {
        String josnMessage = JSONUtil.parseObj(message, false).toString();
        stringRedisTemplate.convertAndSend(channel, josnMessage);
    }
}
