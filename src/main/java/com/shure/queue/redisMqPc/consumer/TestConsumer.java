package com.shure.queue.redisMqPc.consumer;

import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TestConsumer implements Consumer {

    private static final Logger logger = LoggerFactory.getLogger(TestConsumer.class);

    @Override
    public void onMessage(Object message) {
        Map<String, Object> map = JSONUtil.toBean(String.valueOf(message), Map.class);
        logger.info(map.toString());
    }

    @Override
    public void onError(Object message, Exception e) {

    }
}
