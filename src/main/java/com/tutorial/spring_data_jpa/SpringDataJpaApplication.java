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
					new Author("Michael", "Davis", 60, "michael.davis@example.com"),
					new Author("Sarah", "Wilson", 35, "sarah.wilson@example.com"),
					new Author("David", "Martinez", 42, "david.martinez@example.com"),
					new Author("Sophia", "Anderson", 27, "sophia.anderson@example.com"),
					new Author("James", "Taylor", 50, "james.taylor@example.com"),
					new Author("Linda", "Thomas", 47, "linda.thomas@example.com"),
					new Author("Daniel", "Harris", 30, "daniel.harris@example.com"),
					new Author("Laura", "Lewis", 33, "laura.lewis@example.com"),
					new Author("Matthew", "Walker", 36, "matthew.walker@example.com"),
					new Author("Anna", "Hall", 25, "anna.hall@example.com"),
					new Author("Christopher", "Allen", 29, "christopher.allen@example.com"),
					new Author("Patricia", "Young", 55, "patricia.young@example.com"),
					new Author("Joshua", "King", 40, "joshua.king@example.com"),
					new Author("Ashley", "Scott", 31, "ashley.scott@example.com"),
					new Author("Brian", "Green", 48, "brian.green@example.com"),
					new Author("Elizabeth", "Adams", 26, "elizabeth.adams@example.com")
			);

			authorRepository.saveAll(authors);

			System.out.println(authors.size() + " authors persisted!");
		};
	}
}