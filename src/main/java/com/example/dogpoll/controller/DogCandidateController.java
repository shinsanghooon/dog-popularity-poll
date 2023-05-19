package com.example.dogpoll.controller;

import com.example.dogpoll.dto.DogCandidateRequestDto;
import com.example.dogpoll.dto.DogCandidateResponseDto;
import com.example.dogpoll.dto.UpdateProfileImageDto;
import com.example.dogpoll.entity.DogCandidate;
import com.example.dogpoll.service.DogCandidateService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dog-candidates")
@RequiredArgsConstructor
public class DogCandidateController {

    private final DogCandidateService dogCandidateService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<DogCandidateResponseDto> readAllDogCandidates(Pageable pageable) {
        return dogCandidateService.readAllDogCandidates(pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public DogCandidateResponseDto readDogCandidate(@PathVariable("id") Long id) {
        return dogCandidateService.readDogCandidate(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public DogCandidateResponseDto createDogCandidate(@RequestBody DogCandidateRequestDto dogCandidateRequestDto) {
        return dogCandidateService.createDogCandidate(dogCandidateRequestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteDogCandidate(@PathVariable("id") Long id) {
        dogCandidateService.deleteDogCandidate(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public void updateProfileImage(@PathVariable("id") Long id, @RequestBody UpdateProfileImageDto updateProfileImageDto) {
        dogCandidateService.updateProfileImage(id, updateProfileImageDto);
    }
}
