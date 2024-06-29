package com.tomaszbozek.example.vote.vote_worker_service;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@RequiredArgsConstructor
public class VotesReceiver {
    private final VotesRepository votesRepository;
    private final AtomicInteger counter = new AtomicInteger();

    public void receiveMessage(String message) {
        log.info("Received <{}>", message);
        counter.incrementAndGet();
        log.info("working...");
        log.info("vote: {}", message);
        Vote result = votesRepository.save(new Vote(
                UUID.randomUUID(),
                message
        ));
        log.info("insert result: {}", result);
    }

    public int getCount() {
        return counter.get();
    }
}