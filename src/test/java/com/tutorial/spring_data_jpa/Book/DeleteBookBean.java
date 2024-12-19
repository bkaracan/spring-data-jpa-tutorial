package com.tutorial.spring_data_jpa.Book;

import com.tutorial.spring_data_jpa.entity.Book;
import com.tutorial.spring_data_jpa.enums.MessageEnum;
import com.tutorial.spring_data_jpa.enums.ResponseEnum;
import com.tutorial.spring_data_jpa.payload.AbstractResponsePayload;
import com.tutorial.spring_data_jpa.payload.ResponsePayload;
import com.tutorial.spring_data_jpa.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class DeleteBookBean extends AbstractResponsePayload {

    private final BookRepository bookRepository;

    @Autowired
    public DeleteBookBean(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public ResponsePayload<?> deleteById(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);

        return bookOptional.map(book -> {
            bookRepository.delete(book);
            return createSuccessResponse(MessageEnum.DELETE_SUCCESS.getMessage(), null);
        }).orElseGet(() -> createErrorResponse(ResponseEnum.NOT_FOUND, MessageEnum.NOT_FOUND.getMessage(), true));
    }
}