package com.danfy.get_a_pet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class GetAPetApplication {

	public static void main(String[] args) {
		SpringApplication.run(GetAPetApplication.class, args);
	}

}
