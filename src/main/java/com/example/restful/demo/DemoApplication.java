package com.example.restful.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class DemoApplication {




	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

	}


//	@Bean
//	CommandLineRunner runner(userRepository repository) {
//		return args -> {
////			List <User> list = repository.findItemByUsername("mizu");
//// 			list.forEach(item -> {
////
////				System.out.println("username: " + item.getUsername() + " && password: " + item.getPassword());
//// 			});
//
////			User singleUser = repository.findItemByUsername("mizu");
////			singleUser.setEmail("mizu@gmail.com");
////			repository.save(singleUser);
//		};
//	}

}
