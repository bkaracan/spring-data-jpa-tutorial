package com.tutorial.spring_data_jpa.Author;

import com.tutorial.spring_data_jpa.bean.Author.PageAuthorBean;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PageAuthorBeanTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private PageAuthorBean pageAuthorBean;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAuthorsByPage_Success() {
        // Arrange
        int page = 0;
        int size = 2;
        Date birthDate = new Date();


        List<Author> authorEntities = Arrays.asList(
                new Author(1L, "John", "Doe", birthDate, null, "Biography 1"),
                new Author(2L, "Jane", "Smith", birthDate, null, "Biography 2")
        );

        List<AuthorResponseDTO> authorDTOs = Arrays.asList(
                AuthorResponseDTO.builder().id(1L).firstName("John").lastName("Doe").birthDate(birthDate).biography("Biography 1").build(),
                AuthorResponseDTO.builder().id(2L).firstName("Jane").lastName("Smith").birthDate(birthDate).biography("Biography 2").build()
        );


        Pageable pageable = PageRequest.of(page, size);
        Page<Author> authorPage = new PageImpl<>(authorEntities,pageable,authorEntities.size());


        when(authorRepository.findAll(pageable)).thenReturn(authorPage);
        when(authorMapper.toResponseDTO(any(Author.class)))
                .thenReturn(authorDTOs.get(0),authorDTOs.get(1));

        // Act
        ResponsePayload<List<AuthorResponseDTO>> response = pageAuthorBean.getAuthorsByPage(page, size);

        // Assert
        assertNotNull(response);
        assertEquals(ResponseEnum.OK.getHttpStatusCode(), response.getStatusCode());
        assertTrue(response.getIsSuccess());
        assertEquals(MessageEnum.FETCH_SUCCESS.getMessage(), response.getMessage());
        assertNotNull(response.getData());
        assertEquals(size, response.getData().size());
        assertEquals(authorDTOs.get(0).getId(), response.getData().get(0).getId());
        assertEquals(authorDTOs.get(1).getId(), response.getData().get(1).getId());


        verify(authorRepository, times(1)).findAll(pageable);
        verify(authorMapper, times(2)).toResponseDTO(any(Author.class));
    }

    @Test
    void testGetAuthorsByPage_EmptyList() {
        // Arrange
        int page = 0;
        int size = 2;
        Pageable pageable = PageRequest.of(page, size);

        Page<Author> emptyAuthorPage = new PageImpl<>(Collections.emptyList(),pageable,0);

        when(authorRepository.findAll(pageable)).thenReturn(emptyAuthorPage);

        // Act
        ResponsePayload<List<AuthorResponseDTO>> response = pageAuthorBean.getAuthorsByPage(page, size);

        // Assert
        assertNotNull(response);
        assertEquals(ResponseEnum.NOT_FOUND.getHttpStatusCode(), response.getStatusCode());
        assertFalse(response.getIsSuccess());
        assertEquals(MessageEnum.EMPTY_LIST.getMessage(), response.getMessage());
        assertNull(response.getData());


        verify(authorRepository, times(1)).findAll(pageable);
        verify(authorMapper, times(0)).toResponseDTO(any(Author.class));
    }
}