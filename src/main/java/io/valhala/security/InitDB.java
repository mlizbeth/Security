package io.valhala.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class InitDB {
	
	@Bean
	CommandLineRunner init(StudentRepository repo) {
		return args -> {
			log.info("Preloading " + repo.save(new Student("Dwayne", "Wayne", "1337")));
		};
	}

}
