package com.tutorial.spring_data_jpa.Author;

import com.tutorial.spring_data_jpa.bean.Author.DeleteAuthorBean;
import com.tutorial.spring_data_jpa.entity.Author;
import com.tutorial.spring_data_jpa.enums.MessageEnum;
import com.tutorial.spring_data_jpa.enums.ResponseEnum;
import com.tutorial.spring_data_jpa.payload.ResponsePayload;
import com.tutorial.spring_data_jpa.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteAuthorBeanTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private DeleteAuthorBean deleteAuthorBean;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteAuthor_Success() {
        // Arrange
        Long authorId = 1L;
        Date birthDate = new Date();
        Author author = new Author(authorId, "John", "Doe", birthDate,null, "Test Biography");

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        doNothing().when(authorRepository).delete(author);


        // Act
        ResponsePayload<?> response = deleteAuthorBean.deleteById(authorId);

        // Assert
        assertNotNull(response);
        assertTrue(response.getIsSuccess());
        assertEquals(MessageEnum.DELETE_SUCCESS.getMessage(), response.getMessage());
        assertNull(response.getData());

        verify(authorRepository, times(1)).findById(authorId);
        verify(authorRepository, times(1)).delete(author);
    }

    @Test
    void testDeleteAuthor_NotFound() {
        // Arrange
        Long authorId = 1L;
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        // Act
        ResponsePayload<?> response = deleteAuthorBean.deleteById(authorId);

        // Assert
        assertNotNull(response);
        assertFalse(response.getIsSuccess());
        assertEquals(ResponseEnum.NOT_FOUND.getHttpStatusCode(), response.getStatusCode());
        assertEquals(MessageEnum.NOT_FOUND.getMessage(), response.getMessage());
        assertNull(response.getData());

        verify(authorRepository, times(1)).findById(authorId);
        verify(authorRepository, times(0)).delete(any(Author.class));
    }
}