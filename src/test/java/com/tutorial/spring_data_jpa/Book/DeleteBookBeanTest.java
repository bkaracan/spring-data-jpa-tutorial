package com.tutorial.spring_data_jpa.Book;

import com.tutorial.spring_data_jpa.entity.Author;
import com.tutorial.spring_data_jpa.entity.Book;
import com.tutorial.spring_data_jpa.enums.MessageEnum;
import com.tutorial.spring_data_jpa.enums.ResponseEnum;
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
import static org.mockito.Mockito.*;

class DeleteBookBeanTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private DeleteBookBean deleteBookBean;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteBook_Success() {
        // Arrange
        Long bookId = 1L;
        Date publishDate = new Date();
        Author author = new Author(1L, "John", "Doe", new Date(), null, "Test Biography");
        Book book = new Book(bookId, "Book 1", "ISBN-1", publishDate, 200, "Summary 1",author);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).delete(book);

        // Act
        ResponsePayload<?> response = deleteBookBean.deleteById(bookId);

        // Assert
        assertNotNull(response);
        assertTrue(response.getIsSuccess());
        assertEquals(MessageEnum.DELETE_SUCCESS.getMessage(), response.getMessage());
        assertNull(response.getData());

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    void testDeleteBook_NotFound() {
        // Arrange
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act
        ResponsePayload<?> response = deleteBookBean.deleteById(bookId);

        // Assert
        assertNotNull(response);
        assertFalse(response.getIsSuccess());
        assertEquals(ResponseEnum.NOT_FOUND.getHttpStatusCode(), response.getStatusCode());
        assertEquals(MessageEnum.NOT_FOUND.getMessage(), response.getMessage());
        assertNull(response.getData());

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(0)).delete(any(Book.class));
    }
}