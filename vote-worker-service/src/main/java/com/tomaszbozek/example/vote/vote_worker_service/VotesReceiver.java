package com.tomaszbozek.example.vote.vote_worker_service;

import java.util.UUID;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class VotesReceiver {
    private final VotesRepository votesRepository;
    private final ObjectMapper objectMapper;

    public void receiveMessage(String message) {
        log.debug("Received <{}>", message);
        VoteDto voteDto = asDto(message);
        log.debug("Deserialized value: {}", voteDto);
        Vote result = votesRepository.save(new Vote(
                UUID.randomUUID(),
                voteDto.name(),
                voteDto.vote()
        ));
        log.debug("Insert result: {}", result);
    }

    @SneakyThrows
    private VoteDto asDto(String message) {
        return objectMapper.readValue(message, VoteDto.class);
    }

    private record VoteDto(String voterId, String name, String vote){}
}