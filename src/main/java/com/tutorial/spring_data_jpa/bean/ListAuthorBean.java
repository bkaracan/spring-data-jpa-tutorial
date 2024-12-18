package com.tutorial.spring_data_jpa.bean;

import com.tutorial.spring_data_jpa.dto.AuthorResponseDTO;
import com.tutorial.spring_data_jpa.enums.MessageEnum;
import com.tutorial.spring_data_jpa.enums.ResponseEnum;
import com.tutorial.spring_data_jpa.mapper.AuthorMapper;
import com.tutorial.spring_data_jpa.payload.AbstractResponsePayload;
import com.tutorial.spring_data_jpa.payload.ResponsePayload;
import com.tutorial.spring_data_jpa.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListAuthorBean extends AbstractResponsePayload {

    private final AuthorRepository authorRepository;

    @Autowired
    public ListAuthorBean(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public ResponsePayload<List<AuthorResponseDTO>> getAllAuthors() {
        List<AuthorResponseDTO> authorList = AuthorMapper.toResponseDTOList(authorRepository.findAll(Sort.by(Sort.Direction.ASC, "firstName")));

        if (authorList.isEmpty()) {
            return createErrorResponse(ResponseEnum.NOT_FOUND, MessageEnum.EMPTY_LIST, false);
        }

        return createSuccessResponse(MessageEnum.FETCH_SUCCESS.getMessage(), authorList);
    }
}
