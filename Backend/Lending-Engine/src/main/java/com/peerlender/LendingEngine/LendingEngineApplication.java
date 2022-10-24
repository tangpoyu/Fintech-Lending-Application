package com.peerlender.LendingEngine;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LendingEngineApplication{


	public static void main(String[] args) {
		String[] allBeanNames = SpringApplication.run(LendingEngineApplication.class, args).getBeanDefinitionNames();
		for(String beanName : allBeanNames) {
			System.out.println(beanName);
		}
	}
}
