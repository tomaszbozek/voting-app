package com.tomaszbozek.example.vote.vote_ui_api;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/vote")
public class VoteApi {

    @Value("${option.a}")
    private String optionA;

    @Value("${option.b}")
    private String optionB;

    private final StringRedisTemplate redisTemplate;

    public VoteApi(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/options")
    public ResponseEntity<Map<String, String>> getOptions() {
        Map<String, String> options = new HashMap<>();
        options.put("optionA", optionA);
        options.put("optionB", optionB);
        options.put("optionAvalue", "a");
        options.put("optionBvalue", "b");
        return ResponseEntity.ok(options);
    }

    @PostMapping("/")
    public ResponseEntity<String> vote(HttpServletRequest request, HttpServletResponse response,
                                       @RequestBody Map<String, String> voteData) {
        String voterId = getVoterId(request);
        String vote = voteData.get("vote");
        redisTemplate.convertAndSend("votes", "{\"voter_id\":\"" + voterId + "\",\"vote\":\"" + vote + "\"}");
        Cookie cookie = new Cookie("voter_id", voterId);
        cookie.setMaxAge(365 * 24 * 60 * 60);
        response.addCookie(cookie);

        return ResponseEntity.ok("Vote received");
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
}
