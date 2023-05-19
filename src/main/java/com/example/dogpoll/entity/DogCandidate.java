package com.example.dogpoll.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Entity
@Getter
@NoArgsConstructor
public class DogCandidate extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String profileImageUrl;

    @Column
    private String description;

    @Column
    private int votesCount;

    @Builder
    public DogCandidate(String name, String profileImageUrl, String description, int votesCount) {
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.description = description;
        this.votesCount = votesCount;
    }

    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

}
