package com.example.dogpoll.repository;

import com.example.dogpoll.entity.DogPollDuplicateCheck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogPollDuplicateCheckRepository extends JpaRepository<DogPollDuplicateCheck, Long> {

    boolean existsByDogCandidateIdAndUserIp(Long dogCandidateId, String ip);
}
