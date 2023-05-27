package com.example.dogpoll.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

    @Bean
    public NewTopic pollDog() {
        return TopicBuilder
            .name("poll.dog")
            .partitions(1)
            .replicas(1)
            .build();
    }

}
