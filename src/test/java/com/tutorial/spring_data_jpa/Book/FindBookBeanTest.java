package com.tutorial.spring_data_jpa.Book;

import com.tutorial.spring_data_jpa.bean.Book.FindBookBean;
import com.tutorial.spring_data_jpa.dto.BookResponseDTO;
import com.tutorial.spring_data_jpa.entity.Author;
import com.tutorial.spring_data_jpa.entity.Book;
import com.tutorial.spring_data_jpa.enums.MessageEnum;
import com.tutorial.spring_data_jpa.enums.ResponseEnum;
import com.tutorial.spring_data_jpa.mapper.BookMapper;
import com.tutorial.spring_data_jpa.payload.ResponsePayload;
import com.tutorial.spring_data_jpa.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FindBookBeanTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private FindBookBean findBookBean;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_Success() {
        // Arrange
        Long bookId = 1L;
        Date publishDate = new Date();
        Author author = new Author(1L, "John", "Doe", new Date(), null, "Test Biography");


        BookResponseDTO bookDTO = BookResponseDTO.builder()
                .id(bookId)
                .bookName("Book 1")
                .isbn("ISBN-1")
                .publishDate(publishDate)
                .page(200)
                .summary("Summary 1")
                .authorFirstName("John")
                .authorLastName("Doe")
                .build();
        Book bookEntity = new Book(bookId, "Book 1", "ISBN-1", publishDate, 200, "Summary 1",author);



        when(bookRepository.findById(bookId)).thenReturn(Optional.of(bookEntity));
        when(bookMapper.toResponseDTO(any(Book.class))).thenReturn(bookDTO);

        // Act
        ResponsePayload<BookResponseDTO> response = findBookBean.findById(bookId);

        // Assert
        assertNotNull(response);
        assertEquals(ResponseEnum.OK.getHttpStatusCode(), response.getStatusCode());
        assertTrue(response.getIsSuccess());
        assertEquals(MessageEnum.FETCH_SUCCESS.getMessage(), response.getMessage());
        assertEquals(bookDTO.getId(), response.getData().getId());
        assertEquals(bookDTO.getBookName(), response.getData().getBookName());
        assertEquals(bookDTO.getIsbn(), response.getData().getIsbn());
        assertEquals(bookDTO.getPublishDate(), response.getData().getPublishDate());
        assertEquals(bookDTO.getPage(), response.getData().getPage());
        assertEquals(bookDTO.getSummary(), response.getData().getSummary());
        assertEquals(bookDTO.getAuthorFirstName(), response.getData().getAuthorFirstName());
        assertEquals(bookDTO.getAuthorLastName(), response.getData().getAuthorLastName());

        assertFalse(response.getShowNotification());

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookMapper, times(1)).toResponseDTO(any(Book.class));
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        Long bookId = 2L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act
        ResponsePayload<BookResponseDTO> response = findBookBean.findById(bookId);

        // Assert
        assertNotNull(response);
        assertEquals(ResponseEnum.NOT_FOUND.getHttpStatusCode(), response.getStatusCode());
        assertFalse(response.getIsSuccess());
        assertEquals(MessageEnum.NOT_FOUND.getMessage(), response.getMessage());
        assertNull(response.getData());
        assertTrue(response.getShowNotification());

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookMapper, times(0)).toResponseDTO(any(Book.class));
    }
}