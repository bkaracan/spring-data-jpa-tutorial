package com.tutorial.spring_data_jpa.mapper;

import com.tutorial.spring_data_jpa.dto.BookRequestDTO;
import com.tutorial.spring_data_jpa.dto.BookResponseDTO;
import com.tutorial.spring_data_jpa.entity.Author;
import com.tutorial.spring_data_jpa.entity.Book;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {


    public Book toEntity(BookRequestDTO dto, Author author) {
        if (dto == null) {
            return null;
        }
        Book book = new Book();
        book.setBookName(dto.getBookName());
        book.setIsbn(dto.getIsbn());
        book.setPublishDate(dto.getPublishDate());
        book.setPage(dto.getPage());
        book.setSummary(dto.getSummary());
        book.setAuthor(author);
        return book;
    }

    public List<Book> toEntityList(List<BookRequestDTO> dtoList, List<Author> authorList) {
        if (dtoList == null || dtoList.isEmpty()) {
            return List.of();
        }
        return dtoList.stream()
                .map(dto -> {
                    Author author = authorList.stream()
                            .filter(x -> x.getId().equals(dto.getAuthorId()))
                            .findFirst()
                            .orElse(null);
                    return this.toEntity(dto, author);
                })
                .collect(Collectors.toList());
    }

    public BookResponseDTO toResponseDTO(Book entity) {
        if (entity == null) {
            return null;
        }
        return BookResponseDTO.builder()
                .id(entity.getId())
                .bookName(entity.getBookName())
                .isbn(entity.getIsbn())
                .publishDate(entity.getPublishDate())
                .page(entity.getPage())
                .summary(entity.getSummary())
                .authorFirstName(entity.getAuthor().getFirstName())
                .authorLastName(entity.getAuthor().getLastName())
                .build();
    }

    public List<BookResponseDTO> toResponseDTOList(List<Book> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return List.of();
        }
        return entityList.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}