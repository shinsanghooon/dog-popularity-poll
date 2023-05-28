package com.example.dogpoll.service;

import com.example.dogpoll.dto.DogCandidateRequestDto;
import com.example.dogpoll.dto.DogCandidateResponseDto;
import com.example.dogpoll.dto.DogCandidateResponseDtos;
import com.example.dogpoll.dto.UpdateProfileImageDto;
import com.example.dogpoll.entity.DogCandidate;
import com.example.dogpoll.entity.DogPollDuplicateCheck;
import com.example.dogpoll.repository.DogCandidateRepository;
import com.example.dogpoll.repository.DogPollDuplicateCheckRepository;
import com.example.dogpoll.util.IpChecker;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DogCandidateService {

    private final DogCandidateRepository dogCandidateRepository;

    private final DogPollDuplicateCheckRepository dogPollDuplicateCheckRepository;

    /**
     * 전체 강아지 목록 조회
     * @param pageable
     * @return 전체 강아지 리스트
     */
    @Cacheable(cacheNames = "allDogCandidatesCache", key="#root.methodName")
    public DogCandidateResponseDtos readAllDogCandidates(Pageable pageable) {
        Page<DogCandidate> allDogCandidates = dogCandidateRepository.findAll(pageable);

        String ip = IpChecker.getClientIp();
        Map<Long, DogPollDuplicateCheck> dogPollMap = createDogPollMap(
            ip);

        List<DogCandidateResponseDto> dogCandidateResponseDtos = allDogCandidates
            .getContent()
            .stream()
            .map(DogCandidateResponseDto::fromEntity)
            .toList();

        dogCandidateResponseDtos.forEach(responseDtos -> {
            DogPollDuplicateCheck dogPollDuplicateCheck = dogPollMap.get(responseDtos.getId());
            if (dogPollDuplicateCheck != null) {
                if (ip.equals(dogPollDuplicateCheck.getUserIp())) {
                    responseDtos.markVoted();
                }
            }
        });

        return new DogCandidateResponseDtos(dogCandidateResponseDtos);
    }

    /**
     * 특정 강아지 상세 조회
     * @param id 강아지 id
     * @return 조회 요청 강아지
     */
    @Cacheable(cacheNames = "dogCandidateCache", key="#id")
    public DogCandidateResponseDto readDogCandidate(Long id) {
        DogCandidate dogCandidate = getDogCandidate(id);

        String ip = IpChecker.getClientIp();
        Map<Long, DogPollDuplicateCheck> dogPollMap = createDogPollMap(ip);

        DogCandidateResponseDto dogCandidateResponseDto = DogCandidateResponseDto.fromEntity(
            dogCandidate);

        DogPollDuplicateCheck dogPollDuplicateCheck = dogPollMap.get(dogCandidateResponseDto.getId());
        if (dogPollDuplicateCheck != null) {
            if (ip.equals(dogPollDuplicateCheck.getUserIp())) {
                dogCandidateResponseDto.markVoted();
            }
        }

        return dogCandidateResponseDto;
    }

    private Map<Long, DogPollDuplicateCheck> createDogPollMap(String ip) {
        List<DogPollDuplicateCheck> myDogCandidates = dogPollDuplicateCheckRepository.findByUserIp(
            ip);
        return myDogCandidates
            .stream()
            .collect(Collectors.toMap(DogPollDuplicateCheck::getDogCandidateId, dogPoll -> dogPoll));
    }

    /**
     * 강아지 후보 등록
     * @param dogCandidateRequestDto
     * @return 등록된 강아지
     */
    public DogCandidateResponseDto createDogCandidate(
        DogCandidateRequestDto dogCandidateRequestDto) {
        DogCandidate savedDogCandidate = dogCandidateRepository.save(dogCandidateRequestDto.toEntity());
        return DogCandidateResponseDto.fromEntity(savedDogCandidate);

    }

    /**
     * 강아지 후보 삭제
     * @param id 삭제 요청 id
     */
    @CacheEvict(value="dogCandidateCache", key="#id")
    public void deleteDogCandidate(Long id) {
        DogCandidate dogCandidate = getDogCandidate(id);
        dogCandidate.delete();
    }


    /**
     * 강아지 이미지 사진 변경
     * @param id
     * @param updateProfileImageDto
     */
    public void updateProfileImage(Long id, UpdateProfileImageDto updateProfileImageDto) {
        DogCandidate dogCandidate = getDogCandidate(id);
        dogCandidate.updateProfileImageUrl(updateProfileImageDto.getProfileImageUrl());
    }

    /**
     * 강아지 조회
     * @param id 조회할 강아지 id
     * @return 조회 요청 강아지
     */
    private DogCandidate getDogCandidate(Long id) {
        return dogCandidateRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 id에 대한 정보가 없습니다."));
    }

    public DogCandidateResponseDto voteToDogCandidate(Long id) {
        DogCandidate dogCandidate = getDogCandidate(id);
        dogCandidate.getVote();
        return DogCandidateResponseDto.fromEntity(dogCandidate);
    }

    public DogCandidateResponseDto cancelVoteToDogCandidate(Long id) {
        DogCandidate dogCandidate = getDogCandidate(id);
        dogCandidate.cancelVote();
        return DogCandidateResponseDto.fromEntity(dogCandidate);
    }

}
