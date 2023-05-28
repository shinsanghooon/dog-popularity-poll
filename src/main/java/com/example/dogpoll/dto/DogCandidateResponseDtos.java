package com.example.dogpoll.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DogCandidateResponseDtos {

    private List<DogCandidateResponseDto> dogCandidateResponseDtos = new ArrayList<>();

    public DogCandidateResponseDtos(List<DogCandidateResponseDto> dogCandidateResponseDtos) {
        this.dogCandidateResponseDtos = dogCandidateResponseDtos;
    }
}
