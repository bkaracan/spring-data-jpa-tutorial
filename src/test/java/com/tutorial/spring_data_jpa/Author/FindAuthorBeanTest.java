package com.tutorial.spring_data_jpa.Author;

import com.tutorial.spring_data_jpa.bean.Author.FindAuthorBean;
import com.tutorial.spring_data_jpa.dto.AuthorResponseDTO;
import com.tutorial.spring_data_jpa.entity.Author;
import com.tutorial.spring_data_jpa.enums.MessageEnum;
import com.tutorial.spring_data_jpa.enums.ResponseEnum;
import com.tutorial.spring_data_jpa.mapper.AuthorMapper;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FindAuthorBeanTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private FindAuthorBean findAuthorBean;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_Success() {
        Long authorId = 1L;
        Date birthDate = new Date();

        AuthorResponseDTO authorDTO = AuthorResponseDTO.builder()
                .id(authorId)
                .firstName("John")
                .lastName("Doe")
                .birthDate(birthDate)
                .biography("Test Biography")
                .build();
        Author authorEntity = new Author(authorId, "John", "Doe", birthDate, null, "Test Biography");


        when(authorRepository.findById(authorId)).thenReturn(Optional.of(authorEntity));
        when(authorMapper.toResponseDTO(any(Author.class))).thenReturn(authorDTO);

        ResponsePayload<AuthorResponseDTO> response = findAuthorBean.findById(authorId);

        assertNotNull(response);
        assertEquals(ResponseEnum.OK.getHttpStatusCode(), response.getStatusCode());
        assertTrue(response.getIsSuccess());
        assertEquals(MessageEnum.FETCH_SUCCESS.getMessage(), response.getMessage());
        assertEquals(authorDTO.getId(), response.getData().getId());
        assertEquals(authorDTO.getFirstName(), response.getData().getFirstName());
        assertEquals(authorDTO.getLastName(), response.getData().getLastName());
        assertEquals(authorDTO.getBirthDate(), response.getData().getBirthDate());
        assertEquals(authorDTO.getBiography(), response.getData().getBiography());

        assertFalse(response.getShowNotification());

        verify(authorRepository, times(1)).findById(authorId);
        verify(authorMapper, times(1)).toResponseDTO(any(Author.class));
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