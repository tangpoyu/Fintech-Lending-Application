package com.peerlender.Profile;

import com.peerlender.Profile.domain.model.User;
import com.peerlender.Profile.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
@AllArgsConstructor
public class ProfileApplication implements CommandLineRunner {

	private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProfileApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		userRepository.save(new User("John","John","Bongley",31,"Firefighter", LocalDate.now()));
	}
}
