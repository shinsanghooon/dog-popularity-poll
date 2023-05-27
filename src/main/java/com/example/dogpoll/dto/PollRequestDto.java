package com.example.dogpoll.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PollRequestDto implements Serializable {

    private Long dogCandidateId;

    private Boolean isPolled;

    public PollRequestDto(Long dogCandidateId, Boolean isPolled) {
        this.dogCandidateId = dogCandidateId;
        this.isPolled = isPolled;
    }
}
