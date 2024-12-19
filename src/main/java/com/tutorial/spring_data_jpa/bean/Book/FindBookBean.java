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
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindBookBean extends AbstractResponsePayload {

  private final BookRepository bookRepository;

  private final BookMapper bookMapper;

  @Autowired
  public FindBookBean(BookRepository bookRepository, BookMapper bookMapper) {
    this.bookRepository = bookRepository;
      this.bookMapper = bookMapper;
  }

  public ResponsePayload<BookResponseDTO> findById(Long id) {
    Optional<Book> bookOptional = bookRepository.findById(id);

    if (bookOptional.isEmpty()) {
      return createErrorResponse(ResponseEnum.NOT_FOUND, MessageEnum.NOT_FOUND.getMessage(), true);
    }

    Book book = bookOptional.get();
    BookResponseDTO bookResponseDTO = bookMapper.toResponseDTO(book);
    return createSuccessResponse(MessageEnum.FETCH_SUCCESS.getMessage(), bookResponseDTO);
  }
}
