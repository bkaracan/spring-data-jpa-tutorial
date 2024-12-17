package com.tutorial.spring_data_jpa.service;

import com.tutorial.spring_data_jpa.dto.AuthorResponseDTO;
import com.tutorial.spring_data_jpa.payload.ResponsePayload;

public interface AuthorService {

    ResponsePayload<AuthorResponseDTO> findById(Long id);

}
