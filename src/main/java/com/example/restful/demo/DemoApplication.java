package com.example.restful.demo;

import com.example.restful.demo.model.User;
import com.example.restful.demo.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import java.util.List;

@SpringBootApplication
@EnableMongoRepositories
public class DemoApplication {

//	@Autowired
//	userRepository userRepo;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

	}

//	void createUsers() {
//		System.out.println("Data creation started ...");
//		userRepo.save(new User("4", "demo", "demo123@gmail.com", "demo_123"));
//		userRepo.save(new User("4", "azura", "azura123@gmail.com", "azura_123"));
//		System.out.println("Data creation ended ...");
//	}

//	public void run (String... args) {
//		createUsers();
//	}

	@Bean
	CommandLineRunner runner(userRepository repository) {
		return args -> {
			List <User> list = repository.findAll();
 			list.forEach(item -> {

				System.out.println("username: " + item.getUsername() + " && password: " + item.getPassword());
 			});
		};
	}

}
