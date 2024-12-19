package com.tutorial.spring_data_jpa.bean.Author;

import com.tutorial.spring_data_jpa.dto.AuthorResponseDTO;
import com.tutorial.spring_data_jpa.enums.MessageEnum;
import com.tutorial.spring_data_jpa.enums.ResponseEnum;
import com.tutorial.spring_data_jpa.mapper.AuthorMapper;
import com.tutorial.spring_data_jpa.payload.AbstractResponsePayload;
import com.tutorial.spring_data_jpa.payload.ResponsePayload;
import com.tutorial.spring_data_jpa.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class PageAuthorBean extends AbstractResponsePayload {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    @Autowired
    public PageAuthorBean(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public ResponsePayload<List<AuthorResponseDTO>> getAuthorsByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AuthorResponseDTO> authorPage = authorRepository
                .findAll(pageable)
                .map(authorMapper::toResponseDTO);

        if (authorPage.isEmpty()) {
            return createErrorResponse(ResponseEnum.NOT_FOUND, MessageEnum.EMPTY_LIST, false);
        }

        return createSuccessResponse(MessageEnum.FETCH_SUCCESS.getMessage(), authorPage.getContent());
    }
}
