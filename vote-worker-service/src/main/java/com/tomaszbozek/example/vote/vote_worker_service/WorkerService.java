package com.tomaszbozek.example.vote.vote_worker_service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class WorkerService {

    private final RedisTemplate<String, String> template;
    private final VotesRepository votesRepository;
    private final Duration timeLimit = Duration.ofSeconds(10);

    @PostConstruct
    public void start() {
        Instant start = now();
        while (isTimeLimitExceeded(start)) {
            System.out.println("working...");
            String vote = template.opsForList().leftPop("vote");
            System.out.println("vote: " + vote);
            if(vote == null || vote.isBlank()) {
                continue;
            }
            Vote result = votesRepository.save(new Vote(
                    UUID.randomUUID(),
                    vote
            ));
            System.out.println("insert result: " + result);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        int i = 0;
    }

    private boolean isTimeLimitExceeded(Instant start) {
        return now().minusSeconds(timeLimit.toSeconds()).compareTo(start) < 0;
    }

    private static Instant now() {
        return Instant.now(Clock.systemUTC());
    }
}
