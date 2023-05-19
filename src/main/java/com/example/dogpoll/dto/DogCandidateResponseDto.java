package com.example.dogpoll.dto;


import com.example.dogpoll.entity.DogCandidate;
import lombok.Getter;
import lombok.Builder;
@Getter
@Builder
public class DogCandidateResponseDto {

    private Long id;

    private String name;

    private String profileUrl;

    private String description;

    private int votesCount;

    public static DogCandidateResponseDto fromEntity(DogCandidate dogCandidate) {
        return DogCandidateResponseDto.builder()
            .id(dogCandidate.getId())
            .name(dogCandidate.getName())
            .profileUrl(dogCandidate.getProfileUrl())
            .description(dogCandidate.getDescription())
            .votesCount(dogCandidate.getVotesCount())
            .build();
    }

}
