package com.tutorial.spring_data_jpa.bean.Book;

import com.tutorial.spring_data_jpa.dto.BookResponseDTO;
import com.tutorial.spring_data_jpa.entity.Book;
import com.tutorial.spring_data_jpa.enums.MessageEnum;
import com.tutorial.spring_data_jpa.enums.ResponseEnum;
import com.tutorial.spring_data_jpa.mapper.BookMapper;
import com.tutorial.spring_data_jpa.payload.AbstractResponsePayload;
import com.tutorial.spring_data_jpa.payload.ResponsePayload;
import com.tutorial.spring_data_jpa.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PageBookBean extends AbstractResponsePayload {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public PageBookBean(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public ResponsePayload<List<BookResponseDTO>> getBooksByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookRepository.findAll(pageable);
        Page<BookResponseDTO> bookResponseDTOPage = bookPage.map(bookMapper::toResponseDTO);

        if (bookResponseDTOPage.isEmpty()) {
            return createErrorResponse(ResponseEnum.NOT_FOUND, MessageEnum.EMPTY_LIST.getMessage(), false);
        }

        return createSuccessResponse(MessageEnum.FETCH_SUCCESS.getMessage(), bookResponseDTOPage.getContent());
    }


    public ResponsePayload<List<BookResponseDTO>> getBooksByAuthorIdByPage(Long authorId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookRepository.findByAuthorId(authorId,pageable);
        Page<BookResponseDTO> bookResponseDTOPage = bookPage.map(bookMapper::toResponseDTO);


        if (bookResponseDTOPage.isEmpty()) {
            return createErrorResponse(ResponseEnum.NOT_FOUND, MessageEnum.EMPTY_LIST.getMessage(), false);
        }

        return createSuccessResponse(MessageEnum.FETCH_SUCCESS.getMessage(), bookResponseDTOPage.getContent());
    }
}