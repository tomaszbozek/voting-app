package com.tomaszbozek.example.vote.vote_worker_service;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VotesRepository extends ListCrudRepository<Vote, UUID> {
}
