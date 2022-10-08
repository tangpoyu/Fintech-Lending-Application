package com.peerlender.securityapp;

import com.peerlender.securityapp.user.model.User;
import com.peerlender.securityapp.user.model.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class SecurityappApplication implements CommandLineRunner {

	private final UserRepository userRepository;

	public static void main(String[] args) {
		String[] allBeanNames = SpringApplication.run(SecurityappApplication.class, args).getBeanDefinitionNames();
		for(String beanName : allBeanNames) {
			System.out.println(beanName);
		}
	}

	@Override
	public void run(String... args) throws Exception {
		userRepository.save(new User("John","123455"));
		userRepository.save(new User("Peter","123455"));
		userRepository.save(new User("Henry","123455"));
	}
}
