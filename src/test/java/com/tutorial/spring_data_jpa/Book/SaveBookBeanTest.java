package com.tutorial.spring_data_jpa.Book;

import com.tutorial.spring_data_jpa.bean.Book.SaveBookBean;
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

class SaveBookBeanTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private SaveBookBean saveBookBean;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBook_Success() throws ParseException {
        // Arrange
        Date publishDate = new Date();
        Author author = new Author(1L, "John", "Doe", new Date(), null, "Test Biography");
        BookRequestDTO requestDTO = new BookRequestDTO();
        requestDTO.setBookName("Book 1");
        requestDTO.setIsbn("ISBN-1");
        requestDTO.setPublishDate(publishDate);
        requestDTO.setPage(200);
        requestDTO.setSummary("Summary 1");
        requestDTO.setAuthorId(1L);

        Book savedBook = new Book(1L, "Book 1", "ISBN-1", publishDate, 200, "Summary 1",author);

        BookResponseDTO bookResponseDTO = BookResponseDTO.builder()
                .id(1L)
                .bookName("Book 1")
                .isbn("ISBN-1")
                .publishDate(publishDate)
                .page(200)
                .summary("Summary 1")
                .authorFirstName("John")
                .authorLastName("Doe")
                .build();


        when(authorRepository.findById(requestDTO.getAuthorId())).thenReturn(Optional.of(author));
        when(bookRepository.findByBookName(requestDTO.getBookName())).thenReturn(Optional.empty());
        when(bookRepository.findByIsbn(requestDTO.getIsbn())).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);
        when(bookMapper.toResponseDTO(any(Book.class))).thenReturn(bookResponseDTO);
        when(bookMapper.toEntity(any(BookRequestDTO.class),any(Author.class))).thenReturn(savedBook);


        // Act
        ResponsePayload<BookResponseDTO> response = saveBookBean.save(requestDTO);

        // Assert
        assertNotNull(response);
        assertTrue(response.getIsSuccess());
        assertEquals(MessageEnum.SAVE_SUCCESS.getMessage(), response.getMessage());
        assertNotNull(response.getData());
        assertEquals(savedBook.getId(), response.getData().getId());
        assertEquals(savedBook.getBookName(), response.getData().getBookName());
        assertEquals(savedBook.getIsbn(), response.getData().getIsbn());
        assertEquals(savedBook.getPublishDate(), response.getData().getPublishDate());
        assertEquals(savedBook.getPage(), response.getData().getPage());
        assertEquals(savedBook.getAuthor().getFirstName(), response.getData().getAuthorFirstName());
        assertEquals(savedBook.getAuthor().getLastName(), response.getData().getAuthorLastName());
        assertEquals(savedBook.getSummary(),response.getData().getSummary());

        verify(authorRepository, times(1)).findById(requestDTO.getAuthorId());
        verify(bookRepository, times(1)).findByBookName(requestDTO.getBookName());
        verify(bookRepository, times(1)).findByIsbn(requestDTO.getIsbn());
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(bookMapper, times(1)).toResponseDTO(any(Book.class));
        verify(bookMapper, times(1)).toEntity(any(BookRequestDTO.class),any(Author.class));
    }

    @Test
    void testSaveBook_AuthorNotFound() {
        // Arrange
        BookRequestDTO requestDTO = new BookRequestDTO();
        requestDTO.setAuthorId(1L);
        when(authorRepository.findById(requestDTO.getAuthorId())).thenReturn(Optional.empty());


        // Act
        ResponsePayload<BookResponseDTO> response = saveBookBean.save(requestDTO);


        // Assert
        assertNotNull(response);
        assertFalse(response.getIsSuccess());
        assertEquals(ResponseEnum.NOT_FOUND.getHttpStatusCode(), response.getStatusCode());
        assertEquals(MessageEnum.AUTHOR_NOT_FOUND.getMessage(), response.getMessage());

        verify(authorRepository, times(1)).findById(requestDTO.getAuthorId());
        verify(bookRepository, times(0)).save(any(Book.class));
    }


    @Test
    void testSaveBook_AlreadyExists() {
        // Arrange
        Date publishDate = new Date();
        Author author = new Author(1L, "John", "Doe", new Date(), null, "Test Biography");
        BookRequestDTO requestDTO = new BookRequestDTO();
        requestDTO.setBookName("Book 1");
        requestDTO.setIsbn("ISBN-1");
        requestDTO.setPublishDate(publishDate);
        requestDTO.setPage(200);
        requestDTO.setSummary("Summary 1");
        requestDTO.setAuthorId(1L);

        Book book = new Book(1L, "Book 1", "ISBN-1", publishDate, 200, "Summary 1",author);

        when(authorRepository.findById(requestDTO.getAuthorId())).thenReturn(Optional.of(author));
        when(bookRepository.findByBookName(requestDTO.getBookName())).thenReturn(Optional.of(book));

        // Act
        ResponsePayload<BookResponseDTO> response = saveBookBean.save(requestDTO);

        // Assert
        assertNotNull(response);
        assertFalse(response.getIsSuccess());
        assertEquals(ResponseEnum.BAD_REQUEST.getHttpStatusCode(), response.getStatusCode());
        assertEquals(MessageEnum.BOOK_ALREADY_EXISTS.getMessage(), response.getMessage());


        verify(authorRepository, times(1)).findById(requestDTO.getAuthorId());
        verify(bookRepository, times(1)).findByBookName(requestDTO.getBookName());
        verify(bookRepository, times(0)).save(any(Book.class));
    }

    @Test
    void testSaveBook_ISBNAlreadyExists() {
        // Arrange
        Date publishDate = new Date();
        Author author = new Author(1L, "John", "Doe", new Date(), null, "Test Biography");
        BookRequestDTO requestDTO = new BookRequestDTO();
        requestDTO.setBookName("Book 1");
        requestDTO.setIsbn("ISBN-1");
        requestDTO.setPublishDate(publishDate);
        requestDTO.setPage(200);
        requestDTO.setSummary("Summary 1");
        requestDTO.setAuthorId(1L);

        Book book = new Book(1L, "Book 1", "ISBN-1", publishDate, 200, "Summary 1",author);

        when(authorRepository.findById(requestDTO.getAuthorId())).thenReturn(Optional.of(author));
        when(bookRepository.findByIsbn(requestDTO.getIsbn())).thenReturn(Optional.of(book));

        // Act
        ResponsePayload<BookResponseDTO> response = saveBookBean.save(requestDTO);


        // Assert
        assertNotNull(response);
        assertFalse(response.getIsSuccess());
        assertEquals(ResponseEnum.BAD_REQUEST.getHttpStatusCode(), response.getStatusCode());
        assertEquals(MessageEnum.BOOK_ALREADY_EXISTS.getMessage(), response.getMessage());

        verify(authorRepository, times(1)).findById(requestDTO.getAuthorId());
        verify(bookRepository, times(1)).findByIsbn(requestDTO.getIsbn());
        verify(bookRepository, times(0)).save(any(Book.class));
    }
}