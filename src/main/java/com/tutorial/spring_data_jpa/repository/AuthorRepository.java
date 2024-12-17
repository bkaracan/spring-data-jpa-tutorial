package com.tutorial.spring_data_jpa.repository;

import com.tutorial.spring_data_jpa.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {}
