package com.peerlender.LendingEngine;


import com.peerlender.LendingEngine.domain.model.User;
import com.peerlender.LendingEngine.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LendingEngineApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		String[] allBeanNames = SpringApplication.run(LendingEngineApplication.class, args).getBeanDefinitionNames();
		for(String beanName : allBeanNames) {
			System.out.println(beanName);
		}
	}

	@Override
	public void run(String... args) throws Exception {
		userRepository.save(new User("John","John", "B", "Software Developer", 27 ));
		userRepository.save(new User("Peter","Peter","C", "Pilot",21));
		userRepository.save(new User("Henry","Henry","E", "Unemployed",25));
	}
}
