package com.shure.queue.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author weijj
 * @date 2018/7/25 15:20
 */
@Configuration
@EnableCaching
public class RedisConfig {

    private static final long serialVersionUID = 1L;

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheManager.RedisCacheManagerBuilder redisCacheManagerBuilder = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory);

        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        //默认缓存配置
        RedisCacheConfiguration redisCacheDefaultConfiguration = RedisCacheConfigurationBuilder.getInstance()
                .entryTtl(Duration.ofDays(2))
                .serializeValuesWith(jackson2JsonRedisSerializer)
                .build();
        redisCacheManagerBuilder.cacheDefaults(redisCacheDefaultConfiguration);

        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        redisCacheManagerBuilder.withInitialCacheConfigurations(cacheConfigurations);
        return redisCacheManagerBuilder.build();

    }

    /**
     * redis缓存配置构建器
     *
     * @author wuxie
     * @date 2018-8-24
     */
    public static class RedisCacheConfigurationBuilder {

        private RedisCacheConfiguration redisCacheConfiguration;

        public static RedisCacheConfigurationBuilder getInstance() {
            RedisCacheConfigurationBuilder redisCacheConfigurationBuilder = new RedisCacheConfigurationBuilder();
            redisCacheConfigurationBuilder.redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
            return redisCacheConfigurationBuilder;
        }

        public RedisCacheConfigurationBuilder keyPrefix(String keyPrefix) {
            this.redisCacheConfiguration = this.redisCacheConfiguration.prefixKeysWith(keyPrefix + ":");
            return this;
        }

        public RedisCacheConfigurationBuilder computePrefixWith(CacheKeyPrefix cacheKeyPrefix) {
            this.redisCacheConfiguration = this.redisCacheConfiguration.computePrefixWith(cacheKeyPrefix);
            return this;
        }

        public RedisCacheConfigurationBuilder disableKeyPrefix() {
            this.redisCacheConfiguration = this.redisCacheConfiguration.disableKeyPrefix();
            return this;
        }

        public RedisCacheConfigurationBuilder entryTtl(Duration ttl) {
            this.redisCacheConfiguration = this.redisCacheConfiguration.entryTtl(ttl);
            return this;
        }

        public <T> RedisCacheConfigurationBuilder serializeValuesWith(RedisSerializer<T> serializer) {
            this.redisCacheConfiguration = this.redisCacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));
            return this;
        }

        public RedisCacheConfiguration build() {
            this.redisCacheConfiguration = this.redisCacheConfiguration.disableCachingNullValues();
            return this.redisCacheConfiguration;
        }
    }
}
