package com.example.dogpoll.service;

import com.example.dogpoll.dto.PollRequestDto;
import com.example.dogpoll.entity.DogPollDuplicateCheck;
import com.example.dogpoll.repository.DogPollDuplicateCheckRepository;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PollService {

    private final KafkaTemplate<String, PollRequestDto> pollKafkaTemplate;

    private final DogCandidateService dogCandidateService;

    private final DogPollDuplicateCheckRepository dogPollDuplicateCheckRepository;

    public void producePollToKafka(PollRequestDto pollRequestDto, String ip) {

        Long dogCandidateId = pollRequestDto.getDogCandidateId();
        boolean existByIdAndUserIp = dogPollDuplicateCheckRepository.existsByDogCandidateIdAndUserIp(
            dogCandidateId, ip);

        PollRequestDto producePollInfo;
        if (existByIdAndUserIp) {
            producePollInfo = new PollRequestDto(dogCandidateId, true);
        } else {
            producePollInfo = pollRequestDto;
        }

        ListenableFuture<SendResult<String, PollRequestDto>> future = pollKafkaTemplate.send(
            "poll.dog", producePollInfo);

        future.addCallback(new KafkaSendCallback<>() {
            @Override
            public void onSuccess(SendResult<String, PollRequestDto> result) {
                log.info("Success Producer to Kafka");
                if (!existByIdAndUserIp) {
                    dogPollDuplicateCheckRepository.save(new DogPollDuplicateCheck(dogCandidateId, ip));
                }
            }

            @Override
            public void onFailure(KafkaProducerException ex) {
                log.error("Fail Producer to Kafka");
            }
        });
    }

    @KafkaListener(topics="poll.dog", groupId = "poll", containerFactory = "pollConsumer")
    public void consumePollFromKafka(PollRequestDto poll){
        Boolean isPolled = poll.getIsPolled();
        Long dogCandidateId = poll.getDogCandidateId();

        if (Boolean.TRUE.equals(isPolled)) {
            dogCandidateService.cancelVoteToDogCandidate(dogCandidateId);
        } else {
            dogCandidateService.voteToDogCandidate(dogCandidateId);
        }
    }
}
