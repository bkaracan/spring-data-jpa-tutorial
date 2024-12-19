package com.tutorial.spring_data_jpa.bean.Book;

import com.tutorial.spring_data_jpa.dto.BookRequestDTO;
import com.tutorial.spring_data_jpa.dto.BookResponseDTO;
import com.tutorial.spring_data_jpa.entity.Author;
import com.tutorial.spring_data_jpa.entity.Book;
import com.tutorial.spring_data_jpa.enums.MessageEnum;
import com.tutorial.spring_data_jpa.enums.ResponseEnum;
import com.tutorial.spring_data_jpa.mapper.BookMapper;
import com.tutorial.spring_data_jpa.payload.AbstractResponsePayload;
import com.tutorial.spring_data_jpa.payload.ResponsePayload;
import com.tutorial.spring_data_jpa.repository.AuthorRepository;
import com.tutorial.spring_data_jpa.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class SaveBookBean extends AbstractResponsePayload {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

    @Autowired
    public SaveBookBean(BookRepository bookRepository, AuthorRepository authorRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookMapper = bookMapper;
    }

    @Transactional
    public ResponsePayload<BookResponseDTO> save(BookRequestDTO requestDTO) {
        Optional<Author> authorOptional = authorRepository.findById(requestDTO.getAuthorId());
        if(authorOptional.isEmpty()){
            return createErrorResponse(ResponseEnum.NOT_FOUND, MessageEnum.AUTHOR_NOT_FOUND.getMessage(), true);
        }
        Author author = authorOptional.get();

        Optional<Book> existingBookByName = bookRepository.findByBookName(requestDTO.getBookName());
        if(existingBookByName.isPresent()){
            return createErrorResponse(ResponseEnum.BAD_REQUEST, MessageEnum.BOOK_ALREADY_EXISTS.getMessage(),true);
        }
        Optional<Book> existingBookByISBN = bookRepository.findByIsbn(requestDTO.getIsbn());
        if(existingBookByISBN.isPresent()){
            return createErrorResponse(ResponseEnum.BAD_REQUEST, MessageEnum.BOOK_ALREADY_EXISTS.getMessage(),true);
        }


        Book book = bookMapper.toEntity(requestDTO,author);
        Book savedBook = bookRepository.save(book);

        return createSuccessResponse(MessageEnum.SAVE_SUCCESS.getMessage(), bookMapper.toResponseDTO(savedBook));
    }
}