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
    public ResponseEntity<Map<String, String>> getOptions() {
        Map<String, String> options = new HashMap<>();
        options.put("match1.optionA", "Poland");
        options.put("match1.optionB", "France");
        options.put("match1.optionA.value", "a");
        options.put("match1.optionB.value", "b");
        options.put("match2.optionA", "England");
        options.put("match2.optionB", "Slovakia");
        options.put("match2.optionA.value", "a");
        options.put("match2.optionB.value", "b");
        return ResponseEntity.ok(options);
    }

    @PostMapping
    public ResponseEntity<VoteDto> vote(HttpServletRequest request, HttpServletResponse response,
                                       @RequestBody Map<String, String> voteData) {
        log.debug("voteData:{}", voteData);
        String voterId = getVoterId(request);
        String vote = voteData.get("vote");
        VoteDto voteDto = new VoteDto(voterId, vote);
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

    public record VoteDto(String voterId, String vote){}
}
