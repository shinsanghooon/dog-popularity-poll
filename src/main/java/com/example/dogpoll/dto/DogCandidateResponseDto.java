package com.example.dogpoll.dto;

import com.example.dogpoll.entity.DogCandidate;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DogCandidateResponseDto {

    private Long id;

    private String name;

    private String profileUrl;

    private String description;

    private int votesCount;

    private boolean myVote = false;

    @Builder
    public DogCandidateResponseDto(Long id, String name, String profileUrl, String description,
        int votesCount) {
        this.id = id;
        this.name = name;
        this.profileUrl = profileUrl;
        this.description = description;
        this.votesCount = votesCount;
    }

    public static DogCandidateResponseDto fromEntity(DogCandidate dogCandidate) {
        return DogCandidateResponseDto.builder()
            .id(dogCandidate.getId())
            .name(dogCandidate.getName())
            .profileUrl(dogCandidate.getProfileImageUrl())
            .description(dogCandidate.getDescription())
            .votesCount(dogCandidate.getVotesCount())
            .build();
    }

    public void markVoted() {
            this.myVote = true;
    }

}
