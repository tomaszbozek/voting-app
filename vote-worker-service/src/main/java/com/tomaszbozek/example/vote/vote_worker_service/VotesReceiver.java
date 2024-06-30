package com.tomaszbozek.example.vote.vote_worker_service;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@RequiredArgsConstructor
public class VotesReceiver {
    private final VotesRepository votesRepository;
    private final AtomicInteger counter = new AtomicInteger();
    private final ObjectMapper objectMapper;

    public void receiveMessage(String message) {
        log.debug("Received <{}>", message);
        counter.incrementAndGet();
        log.debug("working...");
        log.debug("vote: {}", message);
        VoteDto voteDto = asDto(message);
        log.debug("deserialized value: {}", voteDto);
        Vote result = votesRepository.save(new Vote(
                UUID.randomUUID(),
                voteDto.vote()
        ));
        log.debug("insert result: {}", result);
    }

    @SneakyThrows
    private VoteDto asDto(String message) {
        return objectMapper.readValue(message, VoteDto.class);
    }

    public int getCount() {
        return counter.get();
    }

    private record VoteDto(String voterId, String vote){}
}