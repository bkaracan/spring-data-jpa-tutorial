package com.tutorial.spring_data_jpa.bean.Author;

import com.tutorial.spring_data_jpa.enums.MessageEnum;
import com.tutorial.spring_data_jpa.enums.ResponseEnum;
import com.tutorial.spring_data_jpa.payload.AbstractResponsePayload;
import com.tutorial.spring_data_jpa.payload.ResponsePayload;
import com.tutorial.spring_data_jpa.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeleteAuthorBean extends AbstractResponsePayload {

    private AuthorRepository authorRepository;

    @Autowired
    public DeleteAuthorBean(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public ResponsePayload<?> deleteById(Long id) {
        return authorRepository.findById(id)
                .map(author -> {
                    authorRepository.delete(author);
                    return createSuccessResponse(MessageEnum.DELETE_SUCCESS.getMessage(), null);
                })
                .orElseGet(() -> createErrorResponse(ResponseEnum.NOT_FOUND, MessageEnum.NOT_FOUND, true));
    }
}
