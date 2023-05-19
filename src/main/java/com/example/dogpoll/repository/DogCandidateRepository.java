package com.example.dogpoll.repository;

import com.example.dogpoll.entity.DogCandidate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogCandidateRepository extends JpaRepository<DogCandidate, Long> {

    Optional<DogCandidate> findById(Long id);
}
