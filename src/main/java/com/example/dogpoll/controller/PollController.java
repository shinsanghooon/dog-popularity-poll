package com.example.dogpoll.controller;

import com.example.dogpoll.dto.PollRequestDto;
import com.example.dogpoll.service.PollService;
import com.example.dogpoll.util.IpChecker;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/poll")
@RequiredArgsConstructor
public class PollController {

    private final PollService pollService;

    @Operation(summary = "강아지 후보 투표")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping()
    public void pollToDogCandidate(@RequestBody PollRequestDto pollRequestDto) {
        String ip = IpChecker.getClientIp();
        pollService.producePollToKafka(pollRequestDto, ip);
    }

}
