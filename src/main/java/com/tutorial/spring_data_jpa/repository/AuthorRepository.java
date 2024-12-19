package com.tutorial.spring_data_jpa.repository;

import com.tutorial.spring_data_jpa.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);
}
