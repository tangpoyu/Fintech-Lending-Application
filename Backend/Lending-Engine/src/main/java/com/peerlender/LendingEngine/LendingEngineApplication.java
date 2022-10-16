package com.peerlender.LendingEngine;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LendingEngineApplication implements CommandLineRunner {

//	@Autowired
//	private UserRepository userRepository;

	public static void main(String[] args) {
		String[] allBeanNames = SpringApplication.run(LendingEngineApplication.class, args).getBeanDefinitionNames();
		for(String beanName : allBeanNames) {
			System.out.println(beanName);
		}
	}

	@Override
	public void run(String... args) throws Exception {
//		userRepository.save(new User("John","John", "B", "Software Developer", 27 , new Balance()));
//		userRepository.save(new User("Peter","Peter","C", "Pilot",21, new Balance()));
//		userRepository.save(new User("Henry","Henry","E", "Unemployed",25, new Balance()));
	}
}
