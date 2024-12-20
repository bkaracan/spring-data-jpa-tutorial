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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ListBookBean extends AbstractResponsePayload {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;


    @Autowired
    public ListBookBean(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Transactional(readOnly = true)
    public ResponsePayload<List<BookResponseDTO>> getAllBooks() {
        List<Book> bookList = bookRepository.findAll(Sort.by(Sort.Direction.ASC, "bookName"));
        List<BookResponseDTO> bookResponseDTOList = bookMapper.toResponseDTOList(bookList);


        if (bookResponseDTOList.isEmpty()) {
            return createErrorResponse(ResponseEnum.NOT_FOUND, MessageEnum.EMPTY_LIST.getMessage(), false);
        }
        return createSuccessResponse(MessageEnum.FETCH_SUCCESS.getMessage(), bookResponseDTOList);
    }

    @Transactional(readOnly = true)
    public ResponsePayload<List<BookResponseDTO>> getBooksByAuthorId(Long authorId) {
        List<Book> bookList = bookRepository.findByAuthorId(authorId,Sort.by(Sort.Direction.ASC, "bookName"));
        List<BookResponseDTO> bookResponseDTOList = bookMapper.toResponseDTOList(bookList);

        if (bookResponseDTOList.isEmpty()) {
            return createErrorResponse(ResponseEnum.NOT_FOUND, MessageEnum.EMPTY_LIST.getMessage(), false);
        }
        return createSuccessResponse(MessageEnum.FETCH_SUCCESS.getMessage(), bookResponseDTOList);
    }
}