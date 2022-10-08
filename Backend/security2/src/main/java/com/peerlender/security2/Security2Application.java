package com.peerlender.security2;

import com.peerlender.security2.dao.RoleRepository;
import com.peerlender.security2.dao.UserRepository;
import com.peerlender.security2.entity.Role;
import com.peerlender.security2.entity.User;
import com.peerlender.security2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class Security2Application implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserService userService;


	public static void main(String[] args) {
		SpringApplication.run(Security2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Role adminRole = new Role("Admin","Admin role.");
		Role userRole = new Role("User","Default role for new created record.");
		roleRepository.save(adminRole);
		roleRepository.save(userRole);

		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(adminRole);
		userService.registerNewUser(new User("admin123", "admin@pass","admin","admin",adminRoles));

//		Set<Role> userRoles = new HashSet<>();
//		userRoles.add(userRole);
//		userService.registerNewUser(new User("raj123","raj@pass","rag","sharma",userRoles));
	}
}
