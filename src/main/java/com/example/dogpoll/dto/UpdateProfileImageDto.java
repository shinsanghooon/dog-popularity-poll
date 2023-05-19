package com.example.dogpoll.dto;

import lombok.Getter;
import lombok.Builder;

@Getter
@Builder
public class UpdateProfileImageDto {

    private String profileImageUrl;
}
