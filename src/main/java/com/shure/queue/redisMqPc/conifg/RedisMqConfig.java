package com.shure.queue.redisMqPc.conifg;

import com.shure.queue.redisMqPc.RedisQueue;
import com.shure.queue.redisMqPc.consumer.ConsumerContainer;
import com.shure.queue.redisMqPc.consumer.TestConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisMqConfig {

    /**
     * 配置消息监听
     *
     * @return 消费者容器
     */
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public ConsumerContainer redisMqConsumerContainer(
            @Autowired RedisTemplate<String, Object> redisTemplate
            , TestConsumer testConsumer
    ) {
        ConsumerContainer config = new ConsumerContainer(redisTemplate);

        config.addConsumer(RedisQueue.builder().queue("test").consumer(testConsumer).build());
        return config;
    }

}
