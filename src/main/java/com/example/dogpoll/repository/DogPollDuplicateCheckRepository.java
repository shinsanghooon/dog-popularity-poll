package com.example.dogpoll.repository;

import com.example.dogpoll.entity.DogPollDuplicateCheck;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogPollDuplicateCheckRepository extends JpaRepository<DogPollDuplicateCheck, Long> {

    boolean existsByDogCandidateIdAndUserIp(Long dogCandidateId, String ip);

    List<DogPollDuplicateCheck> findByUserIp(String ip);

    Optional<DogPollDuplicateCheck> findByDogCandidateIdAndUserIp(Long dogCandidateId, String ip);
}
