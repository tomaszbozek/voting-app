package com.tomaszbozek.example.vote.vote_worker_service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WorkerService {

    private final RedisTemplate<String, Object> template;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WorkerService(RedisTemplate<String, Object> template, JdbcTemplate jdbcTemplate) {
        this.template = template;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void start() {
        while (true) {
            System.out.println("working...");
            Object votes = template.opsForList().leftPop("votes");
            System.out.println("votes: " + votes);
            // TODO FIXME: WATCH OUT FOR SQL INJECTION
            int result = jdbcTemplate.update(
                    String.format("INSERT INTO VOTES (id, vote) VALUES (%s, %s);", UUID.randomUUID(), UUID.randomUUID())
            );
            System.out.println("insert result: " + result);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
