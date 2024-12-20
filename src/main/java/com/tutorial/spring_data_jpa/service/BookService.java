package com.tutorial.spring_data_jpa.service;

import com.tutorial.spring_data_jpa.dto.BookRequestDTO;
import com.tutorial.spring_data_jpa.dto.BookResponseDTO;
import com.tutorial.spring_data_jpa.payload.ResponsePayload;

import java.util.List;

public interface BookService {

    ResponsePayload<BookResponseDTO> findById(Long id);

    ResponsePayload<BookResponseDTO> save(BookRequestDTO bookRequestDTO);

    ResponsePayload<BookResponseDTO> update(Long id, BookRequestDTO bookRequestDTO);

    ResponsePayload<List<BookResponseDTO>> getAll();

    ResponsePayload<?> delete(Long id);

    ResponsePayload<List<BookResponseDTO>> getBooksByPage(int page, int size);

    ResponsePayload<List<BookResponseDTO>> getBooksByAuthorId(Long authorId);
}
