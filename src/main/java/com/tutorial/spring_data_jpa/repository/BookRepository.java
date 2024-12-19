package com.tutorial.spring_data_jpa.repository;

import com.tutorial.spring_data_jpa.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByBookName(String bookName);

    Optional<Book> findByIsbn(String isbn);

    List<Book> findByAuthorId(Long authorId, Sort bookName);

    Page<Book> findByAuthorId(Long authorId, Pageable pageable);
}
