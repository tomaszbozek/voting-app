package com.tomaszbozek.example.vote.vote_worker_service;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@AllArgsConstructor
@Data
@Entity
public class Vote {
    @Id
    private UUID id;
    private String vote;
}


