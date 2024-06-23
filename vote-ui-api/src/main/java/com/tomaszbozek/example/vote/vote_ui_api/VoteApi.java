package com.tomaszbozek.example.vote.vote_ui_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class VoteApi {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public VoteApi(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        redisTemplate.opsForValue().set("votes",
                String.format("{'voter_id': %s, 'vote': %s}", UUID.randomUUID(), name));
        return String.format("Hello %s!", name);
    }
}
