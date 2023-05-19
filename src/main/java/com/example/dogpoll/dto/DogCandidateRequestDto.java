package com.example.dogpoll.dto;

import com.example.dogpoll.entity.DogCandidate;
import lombok.Getter;
import lombok.Builder;

@Getter
public class DogCandidateRequestDto {

    private String name;

    private String profileUrl;

    private String description;

    private int votesCount;

    public DogCandidate toEntity() {
        return DogCandidate.builder()
            .name(this.name)
            .profileUrl(this.profileUrl)
            .description(this.description)
            .votesCount(0)
            .build();
    }

}
