package com.tutorial.spring_data_jpa;

import com.tutorial.spring_data_jpa.entity.Author;
import com.tutorial.spring_data_jpa.repository.AuthorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SpringDataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(AuthorRepository authorRepository) {
		return args -> {
			List<Author> authors = Arrays.asList(
					new Author("John", "Doe", 45, "john.doe@example.com"),
					new Author("Jane", "Smith", 38, "jane.smith@example.com"),
					new Author("Robert", "Johnson", 52, "robert.johnson@example.com"),
					new Author("Emily", "Brown", 28, "emily.brown@example.com"),
					new Author("Michael", "Davis", 60, "michael.davis@example.com")
			);

			authorRepository.saveAll(authors);

			System.out.println("5 sample authors persisted!");
		};
	}
}