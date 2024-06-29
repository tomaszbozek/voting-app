package com.tomaszbozek.example.vote.vote_ui_api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {

    @Bean
    LettuceConnectionFactory redisConnectionFactory(@Value("${spring.redis.host}") String host,
                                                    @Value("${spring.redis.port}") int port) {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    StringRedisTemplate redisTemplate(@Value("${spring.redis.host}") String host,
                                                @Value("${spring.redis.port}") int port) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory(host, port));
        return template;
    }
}