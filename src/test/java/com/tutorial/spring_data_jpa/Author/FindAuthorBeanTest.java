package com.tutorial.spring_data_jpa.Author;

import com.tutorial.spring_data_jpa.bean.FindAuthorBean;
import com.tutorial.spring_data_jpa.dto.AuthorResponseDTO;
import com.tutorial.spring_data_jpa.entity.Author;
import com.tutorial.spring_data_jpa.enums.MessageEnum;
import com.tutorial.spring_data_jpa.enums.ResponseEnum;
import com.tutorial.spring_data_jpa.payload.ResponsePayload;
import com.tutorial.spring_data_jpa.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindAuthorBeanTest {

    @Mock
    private AuthorRepository authorRepository;

    private FindAuthorBean findAuthorBean;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        findAuthorBean = new FindAuthorBean(authorRepository);
    }

    @Test
    void testFindById_Success() {
        Long authorId = 1L;
        AuthorResponseDTO authorDTO = AuthorResponseDTO.builder()
                .id(authorId)
                .firstName("John")
                .lastName("Doe")
                .age(27)
                .email("johndoe@gmail.com")
                .build();
        Author authorEntity = new Author(authorId, "John", "Doe", 27, "johndoe@gmail.com");


        when(authorRepository.findById(authorId)).thenReturn(Optional.of(authorEntity));

        ResponsePayload<AuthorResponseDTO> response = findAuthorBean.findById(authorId);

        assertNotNull(response);
        assertEquals(ResponseEnum.OK.getHttpStatusCode(), response.getStatusCode());
        assertTrue(response.getIsSuccess());
        assertEquals(MessageEnum.FETCH_SUCCESS.getMessage(), response.getMessage());
        assertEquals(authorDTO.getId(), response.getData().getId());
        assertEquals(authorDTO.getFirstName(), response.getData().getFirstName());
        assertEquals(authorDTO.getLastName(), response.getData().getLastName());
        assertEquals(authorDTO.getAge(), response.getData().getAge());
        assertEquals(authorDTO.getEmail(), response.getData().getEmail());
        assertFalse(response.getShowNotification());

        verify(authorRepository, times(1)).findById(authorId);
    }

    @Test
    void testFindById_NotFound() {
        Long authorId = 2L;

        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        ResponsePayload<AuthorResponseDTO> response = findAuthorBean.findById(authorId);

        assertNotNull(response);
        assertEquals(ResponseEnum.NOT_FOUND.getHttpStatusCode(), response.getStatusCode());
        assertFalse(response.getIsSuccess());
        assertEquals(MessageEnum.NOT_FOUND.getMessage(), response.getMessage());
        assertNull(response.getData());
        assertTrue(response.getShowNotification());

        verify(authorRepository, times(1)).findById(authorId);
    }
}