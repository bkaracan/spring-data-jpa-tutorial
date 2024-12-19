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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UpdateBookBean extends AbstractResponsePayload {

  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;
  private final BookMapper bookMapper;

  @Autowired
  public UpdateBookBean(BookRepository bookRepository, AuthorRepository authorRepository, BookMapper bookMapper) {
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
      this.bookMapper = bookMapper;
  }

  @Transactional
  public ResponsePayload<BookResponseDTO> updateById(Long id, BookRequestDTO requestDTO) {

    Optional<Book> existingBookOpt = bookRepository.findById(id);

    if (existingBookOpt.isEmpty()) {
      return createErrorResponse(ResponseEnum.NOT_FOUND, MessageEnum.NOT_FOUND.getMessage(), true);
    }
    Optional<Author> authorOptional = authorRepository.findById(requestDTO.getAuthorId());
    if (authorOptional.isEmpty()) {
      return createErrorResponse(
          ResponseEnum.NOT_FOUND, MessageEnum.AUTHOR_NOT_FOUND.getMessage(), true);
    }
    Author author = authorOptional.get();

    Optional<Book> existingBookByName = bookRepository.findByBookName(requestDTO.getBookName());
    if (existingBookByName.isPresent()
        && !existingBookOpt.get().getId().equals(existingBookByName.get().getId())) {
      return createErrorResponse(
          ResponseEnum.BAD_REQUEST, MessageEnum.BOOK_ALREADY_EXISTS.getMessage(), true);
    }
    Optional<Book> existingBookByISBN = bookRepository.findByIsbn(requestDTO.getIsbn());
    if (existingBookByISBN.isPresent()
        && !existingBookOpt.get().getId().equals(existingBookByISBN.get().getId())) {
      return createErrorResponse(
          ResponseEnum.BAD_REQUEST, MessageEnum.BOOK_ALREADY_EXISTS.getMessage(), true);
    }

    Book existingBook = existingBookOpt.get();

    existingBook.setBookName(requestDTO.getBookName());
    existingBook.setIsbn(requestDTO.getIsbn());
    existingBook.setPublishDate(requestDTO.getPublishDate());
    existingBook.setPage(requestDTO.getPage());
    existingBook.setSummary(requestDTO.getSummary());
    existingBook.setAuthor(author);

    Book updatedBook = bookRepository.save(existingBook);

    BookResponseDTO responseDTO = bookMapper.toResponseDTO(updatedBook);
    return createSuccessResponse(MessageEnum.UPDATE_SUCCESS.getMessage(), responseDTO);
  }
}
