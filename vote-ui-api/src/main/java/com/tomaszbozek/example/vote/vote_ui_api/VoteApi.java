package com.tomaszbozek.example.vote.vote_ui_api;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/votes")
public class VoteApi {

    private final StringRedisTemplate redisTemplate;

    public VoteApi(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/options")
    public ResponseEntity<List<OptionDto>> getOptions() {
        return ResponseEntity.ok(List.of(
                new OptionDto("match1", "a", "Poland"),
                new OptionDto("match1", "b", "France"),
                new OptionDto("match2", "a", "England"),
                new OptionDto("match2", "b", "Slovakia")
        ));
    }

    public record OptionDto(String name, String value, String country){}

    @PostMapping
    public ResponseEntity<VoteDto> vote(HttpServletRequest request, HttpServletResponse response,
                                       @RequestBody Map<String, String> voteData) {
        log.debug("voteData:{}", voteData);
        String voterId = getVoterId(request);
        String vote = voteData.get("vote");
        String name = voteData.get("name");
        VoteDto voteDto = new VoteDto(voterId, name, vote);
        redisTemplate.convertAndSend("vote", voteDto);
        Cookie cookie = new Cookie("voter_id", voterId);
        cookie.setMaxAge(365 * 24 * 60 * 60);
        response.addCookie(cookie);
        return ResponseEntity.ok(voteDto);
    }

    private String getVoterId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("voter_id".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return UUID.randomUUID().toString();
    }

    public record VoteDto(String voterId, String name, String vote){}
}
