package com.shure.queue.queue.config;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.shure.queue.queue.consumer.LogConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.concurrent.*;

@Configuration
public class SubListenerConfig {
    /**
     * 初始化监听器
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter1,
                                            MessageListenerAdapter listenerAdapter2) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        container.addMessageListener(listenerAdapter1, new PatternTopic("test"));
//        container.addMessageListener(listenerAdapter2, new PatternTopic("test"));

        /**
         * 如果不定义线程池，每一次消费都会创建一个线程，如果业务层面不做限制，就会导致秒杀超卖
         */
        ThreadFactory factory = new ThreadFactoryBuilder().setNamePrefix("redis-listener-pool-%d").build();
        Executor executor = new ThreadPoolExecutor(
                3,
                10,
                10L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000),
                factory);
        container.setTaskExecutor(executor);
        return container;
    }

    /**
     * 利用反射来创建监听到消息之后的执行方法
     *
     * @param receiver
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter1(LogConsumer receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

//    @Bean
//    MessageListenerAdapter listenerAdapter2(LogConsumer receiver) {
//        return new MessageListenerAdapter(receiver, "receiveMessage");
//    }

    /**
     * 使用默认的工厂初始化redis操作模板
     */
    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }
}
