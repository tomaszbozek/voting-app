package com.tomaszbozek.example.vote.vote_ui_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class VoteUiApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VoteUiApiApplication.class, args);
	}
}
