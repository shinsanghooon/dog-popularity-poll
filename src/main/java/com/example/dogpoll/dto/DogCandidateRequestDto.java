package com.example.dogpoll.dto;

import com.example.dogpoll.entity.DogCandidate;
import lombok.Getter;

@Getter
public class DogCandidateRequestDto {

    private String name;

    private String profileImageUrl;

    private String description;

    private int votesCount = 0;

    public DogCandidate toEntity() {
        return DogCandidate.builder()
            .name(this.name)
            .profileImageUrl(this.profileImageUrl)
            .description(this.description)
            .votesCount(this.votesCount)
            .build();
    }

}
