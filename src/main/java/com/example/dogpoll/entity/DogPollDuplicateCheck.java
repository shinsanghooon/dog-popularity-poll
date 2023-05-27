package com.example.dogpoll.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class DogPollDuplicateCheck extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long dogCandidateId;

    @Column
    private String userIp;

    @Builder
    public DogPollDuplicateCheck(Long dogCandidateId, String userIp) {
        this.dogCandidateId = dogCandidateId;
        this.userIp = userIp;
    }
}
