package com.shure.queue.redisMqPc;


import com.shure.queue.redisMqPc.consumer.Consumer;

public class RedisQueue {

    /**
     * 队列名称
     */
    private String queue;
    /**
     * 消费者
     */
    private Consumer consumer;

    private RedisQueue() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getQueue() {
        return queue;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public static class Builder {
        private RedisQueue configuration = new RedisQueue();

        public RedisQueue defaultConfiguration(Consumer consumer) {
            configuration.consumer = consumer;
            configuration.queue = consumer.getClass().getSimpleName();
            return configuration;
        }

        public Builder queue(String queue) {
            configuration.queue = queue;
            return this;
        }

        public Builder consumer(Consumer consumer) {
            configuration.consumer = consumer;
            return this;
        }

        public RedisQueue build() {
            if (configuration.queue == null || configuration.queue.length() == 0) {
                if (configuration.consumer != null) {
                    configuration.queue = configuration.getClass().getSimpleName();
                }
            }
            return configuration;
        }
    }

}
