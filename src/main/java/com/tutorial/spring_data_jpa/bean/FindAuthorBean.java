package com.tutorial.spring_data_jpa.bean;

import com.tutorial.spring_data_jpa.dto.AuthorResponseDTO;
import com.tutorial.spring_data_jpa.enums.MessageEnum;
import com.tutorial.spring_data_jpa.enums.ResponseEnum;
import com.tutorial.spring_data_jpa.mapper.AuthorMapper;
import com.tutorial.spring_data_jpa.payload.AbstractResponsePayload;
import com.tutorial.spring_data_jpa.payload.ResponsePayload;
import com.tutorial.spring_data_jpa.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindAuthorBean extends AbstractResponsePayload {

    private final AuthorRepository authorRepository;

    @Autowired
    public FindAuthorBean(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public ResponsePayload<AuthorResponseDTO> findById(Long id) {
        return authorRepository.findById(id)
                .map(AuthorMapper::toResponseDTO)
                .map(dto -> createSuccessResponse(MessageEnum.FETCH_SUCCESS.getMessage(), dto))
                .orElseGet(() -> createErrorResponse(ResponseEnum.NOT_FOUND, MessageEnum.NOT_FOUND, true));
    }
}
