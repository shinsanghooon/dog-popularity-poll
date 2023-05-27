package com.example.dogpoll;

import com.example.dogpoll.config.AuditorAwareImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
public class DogpollApplication {

	public static void main(String[] args) {
		SpringApplication.run(DogpollApplication.class, args);
	}

	/**
	 * created_by, updated_by 에 대한 사용자 지정을 위한 Bean
	 *
	 * @return AuditorAware
	 */
	@Bean
	public AuditorAware<String> auditorProvider() {
		return new AuditorAwareImpl();
	}
}
