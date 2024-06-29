package com.tomaszbozek.example.vote.vote_worker_service;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@AllArgsConstructor
@Data
@Entity
@Table(name = "votes")
public class Vote {
    @Id
    private UUID id;
    private String vote;
}


