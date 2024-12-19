package com.tutorial.spring_data_jpa.Book;

import com.tutorial.spring_data_jpa.bean.Book.UpdateBookBean;
import com.tutorial.spring_data_jpa.dto.BookRequestDTO;
import com.tutorial.spring_data_jpa.dto.BookResponseDTO;
import com.tutorial.spring_data_jpa.entity.Author;
import com.tutorial.spring_data_jpa.entity.Book;
import com.tutorial.spring_data_jpa.enums.MessageEnum;
import com.tutorial.spring_data_jpa.enums.ResponseEnum;
import com.tutorial.spring_data_jpa.mapper.BookMapper;
import com.tutorial.spring_data_jpa.payload.ResponsePayload;
import com.tutorial.spring_data_jpa.repository.AuthorRepository;
import com.tutorial.spring_data_jpa.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateBookBeanTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private UpdateBookBean updateBookBean;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateBook_Success() throws ParseException {
        // Arrange
        Long bookId = 1L;
        Date publishDate = new Date();
        Author author = new Author(1L, "John", "Doe", new Date(), null, "Test Biography");

        BookRequestDTO requestDTO = new BookRequestDTO();
        requestDTO.setBookName("Updated Book 1");
        requestDTO.setIsbn("ISBN-UPDATED");
        requestDTO.setPublishDate(publishDate);
        requestDTO.setPage(250);
        requestDTO.setSummary("Updated Summary");
        requestDTO.setAuthorId(1L);

        Book existingBook = new Book(bookId, "Book 1", "ISBN-1", publishDate, 200, "Summary 1",author);
        Book updatedBook = new Book(bookId, "Updated Book 1", "ISBN-UPDATED", publishDate, 250, "Updated Summary",author);

        BookResponseDTO bookResponseDTO = BookResponseDTO.builder()
                .id(bookId)
                .bookName("Updated Book 1")
                .isbn("ISBN-UPDATED")
                .publishDate(publishDate)
                .page(250)
                .authorFirstName("John")
                .authorLastName("Doe")
                .summary("Updated Summary")
                .build();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(authorRepository.findById(requestDTO.getAuthorId())).thenReturn(Optional.of(author));
        when(bookRepository.findByBookName(requestDTO.getBookName())).thenReturn(Optional.empty());
        when(bookRepository.findByIsbn(requestDTO.getIsbn())).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);
        when(bookMapper.toResponseDTO(any(Book.class))).thenReturn(bookResponseDTO);

        // Act
        ResponsePayload<BookResponseDTO> response = updateBookBean.updateById(bookId, requestDTO);

        // Assert
        assertNotNull(response);
        assertTrue(response.getIsSuccess());
        assertEquals(MessageEnum.UPDATE_SUCCESS.getMessage(), response.getMessage());
        assertEquals(bookResponseDTO.getId(), response.getData().getId());
        assertEquals(bookResponseDTO.getBookName(), response.getData().getBookName());
        assertEquals(bookResponseDTO.getIsbn(), response.getData().getIsbn());
        assertEquals(bookResponseDTO.getPublishDate(), response.getData().getPublishDate());
        assertEquals(bookResponseDTO.getPage(), response.getData().getPage());
        assertEquals(bookResponseDTO.getSummary(), response.getData().getSummary());
        assertEquals(bookResponseDTO.getAuthorFirstName(),response.getData().getAuthorFirstName());
        assertEquals(bookResponseDTO.getAuthorLastName(), response.getData().getAuthorLastName());

        verify(bookRepository, times(1)).findById(bookId);
        verify(authorRepository, times(1)).findById(requestDTO.getAuthorId());
        verify(bookRepository, times(1)).findByBookName(requestDTO.getBookName());
        verify(bookRepository, times(1)).findByIsbn(requestDTO.getIsbn());
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(bookMapper, times(1)).toResponseDTO(any(Book.class));
    }
    @Test
    void testUpdateBook_NotFound() {
        // Arrange
        Long bookId = 1L;
        BookRequestDTO requestDTO = new BookRequestDTO();
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act
        ResponsePayload<BookResponseDTO> response = updateBookBean.updateById(bookId, requestDTO);

        // Assert
        assertNotNull(response);
        assertFalse(response.getIsSuccess());
        assertEquals(ResponseEnum.NOT_FOUND.getHttpStatusCode(), response.getStatusCode());
        assertEquals(MessageEnum.NOT_FOUND.getMessage(), response.getMessage());
        assertNull(response.getData());

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(0)).save(any(Book.class));
    }
    @Test
    void testUpdateBook_AuthorNotFound() {
        // Arrange
        Long bookId = 1L;
        Date publishDate = new Date();
        Book existingBook = new Book(bookId, "Book 1", "ISBN-1", publishDate, 200, "Summary 1",new Author());
        BookRequestDTO requestDTO = new BookRequestDTO();
        requestDTO.setAuthorId(1L);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(authorRepository.findById(requestDTO.getAuthorId())).thenReturn(Optional.empty());


        // Act
        ResponsePayload<BookResponseDTO> response = updateBookBean.updateById(bookId, requestDTO);

        // Assert
        assertNotNull(response);
        assertFalse(response.getIsSuccess());
        assertEquals(ResponseEnum.NOT_FOUND.getHttpStatusCode(), response.getStatusCode());
        assertEquals(MessageEnum.AUTHOR_NOT_FOUND.getMessage(), response.getMessage());
        assertNull(response.getData());


        verify(bookRepository, times(1)).findById(bookId);
        verify(authorRepository, times(1)).findById(requestDTO.getAuthorId());
        verify(bookRepository, times(0)).save(any(Book.class));
    }


    @Test
    void testUpdateBook_AlreadyExists() throws ParseException {
        // Arrange
        Long bookId = 1L;
        Date publishDate = new Date();
        Author author = new Author(1L, "John", "Doe", new Date(), null, "Test Biography");
        Book existingBook = new Book(bookId, "Book 1", "ISBN-1", publishDate, 200, "Summary 1",author);
        BookRequestDTO requestDTO = new BookRequestDTO();
        requestDTO.setBookName("Updated Book 1");
        requestDTO.setIsbn("ISBN-UPDATED");
        requestDTO.setPublishDate(publishDate);
        requestDTO.setPage(250);
        requestDTO.setSummary("Updated Summary");
        requestDTO.setAuthorId(1L);


        Book book = new Book(2L, "Updated Book 1", "ISBN-2", publishDate, 200, "Summary 2",author);


        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(authorRepository.findById(requestDTO.getAuthorId())).thenReturn(Optional.of(author));
        when(bookRepository.findByBookName(requestDTO.getBookName())).thenReturn(Optional.of(book));

        // Act
        ResponsePayload<BookResponseDTO> response = updateBookBean.updateById(bookId, requestDTO);

        // Assert
        assertNotNull(response);
        assertFalse(response.getIsSuccess());
        assertEquals(ResponseEnum.BAD_REQUEST.getHttpStatusCode(), response.getStatusCode());
        assertEquals(MessageEnum.BOOK_ALREADY_EXISTS.getMessage(), response.getMessage());


        verify(bookRepository, times(1)).findById(bookId);
        verify(authorRepository, times(1)).findById(requestDTO.getAuthorId());
        verify(bookRepository, times(1)).findByBookName(requestDTO.getBookName());
        verify(bookRepository, times(0)).save(any(Book.class));
    }
    @Test
    void testUpdateBook_ISBNAlreadyExists() throws ParseException {
        // Arrange
        Long bookId = 1L;
        Date publishDate = new Date();
        Author author = new Author(1L, "John", "Doe", new Date(), null, "Test Biography");
        Book existingBook = new Book(bookId, "Book 1", "ISBN-1", publishDate, 200, "Summary 1",author);

        BookRequestDTO requestDTO = new BookRequestDTO();
        requestDTO.setBookName("Updated Book 1");
        requestDTO.setIsbn("ISBN-UPDATED");
        requestDTO.setPublishDate(publishDate);
        requestDTO.setPage(250);
        requestDTO.setSummary("Updated Summary");
        requestDTO.setAuthorId(1L);


        Book book = new Book(2L, "Updated Book 2", "ISBN-UPDATED", publishDate, 200, "Summary 2",author);


        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(authorRepository.findById(requestDTO.getAuthorId())).thenReturn(Optional.of(author));
        when(bookRepository.findByIsbn(requestDTO.getIsbn())).thenReturn(Optional.of(book));

        // Act
        ResponsePayload<BookResponseDTO> response = updateBookBean.updateById(bookId, requestDTO);

        // Assert
        assertNotNull(response);
        assertFalse(response.getIsSuccess());
        assertEquals(ResponseEnum.BAD_REQUEST.getHttpStatusCode(), response.getStatusCode());
        assertEquals(MessageEnum.BOOK_ALREADY_EXISTS.getMessage(), response.getMessage());


        verify(bookRepository, times(1)).findById(bookId);
        verify(authorRepository, times(1)).findById(requestDTO.getAuthorId());
        verify(bookRepository, times(1)).findByIsbn(requestDTO.getIsbn());
        verify(bookRepository, times(0)).save(any(Book.class));
    }
}