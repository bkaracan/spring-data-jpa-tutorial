package com.tutorial.spring_data_jpa.service;

import com.tutorial.spring_data_jpa.dto.AuthorRequestDTO;
import com.tutorial.spring_data_jpa.dto.AuthorResponseDTO;
import com.tutorial.spring_data_jpa.payload.ResponsePayload;

import java.util.List;

public interface AuthorService {

    ResponsePayload<AuthorResponseDTO> findById(Long id);

    ResponsePayload<AuthorResponseDTO> save(AuthorRequestDTO authorRequestDTO);

    ResponsePayload<AuthorResponseDTO> update(Long id, AuthorRequestDTO authorRequestDTO);

    ResponsePayload<List<AuthorResponseDTO>> getAll();

    ResponsePayload<?> delete(Long id);

    ResponsePayload<List<AuthorResponseDTO>> getAuthorsByPage(Integer page, Integer size);
}
