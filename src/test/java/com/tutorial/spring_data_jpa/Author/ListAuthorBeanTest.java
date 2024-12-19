package com.tutorial.spring_data_jpa.Author;

import com.tutorial.spring_data_jpa.bean.Author.ListAuthorBean;
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
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListAuthorBeanTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private ListAuthorBean listAuthorBean;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAuthors_Success() {
        // Arrange
        Date birthDate = new Date();

        List<Author> authorEntities = Arrays.asList(
                new Author(1L, "John", "Doe", birthDate, null, "Biography 1"),
                new Author(2L, "Jane", "Smith", birthDate, null, "Biography 2")
        );
        List<AuthorResponseDTO> authorDTOs = Arrays.asList(
                AuthorResponseDTO.builder().id(1L).firstName("John").lastName("Doe").birthDate(birthDate).biography("Biography 1").build(),
                AuthorResponseDTO.builder().id(2L).firstName("Jane").lastName("Smith").birthDate(birthDate).biography("Biography 2").build()
        );

        when(authorRepository.findAll(Sort.by(Sort.Direction.ASC, "firstName"))).thenReturn(authorEntities);
        when(authorMapper.toResponseDTOList(authorEntities)).thenReturn(authorDTOs);

        // Act
        ResponsePayload<List<AuthorResponseDTO>> response = listAuthorBean.getAllAuthors();

        // Assert
        assertNotNull(response);
        assertEquals(ResponseEnum.OK.getHttpStatusCode(), response.getStatusCode());
        assertTrue(response.getIsSuccess());
        assertEquals(MessageEnum.FETCH_SUCCESS.getMessage(), response.getMessage());
        assertEquals(authorDTOs.size(), response.getData().size());
        assertEquals(authorDTOs.get(0).getId(), response.getData().get(0).getId());
        assertEquals(authorDTOs.get(1).getId(), response.getData().get(1).getId());
        assertFalse(response.getShowNotification());

        verify(authorRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "firstName"));
        verify(authorMapper, times(1)).toResponseDTOList(authorEntities);

    }

    @Test
    void testGetAllAuthors_EmptyList() {
        // Arrange
        when(authorRepository.findAll(Sort.by(Sort.Direction.ASC, "firstName"))).thenReturn(Collections.emptyList());
        when(authorMapper.toResponseDTOList(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Act
        ResponsePayload<List<AuthorResponseDTO>> response = listAuthorBean.getAllAuthors();

        // Assert
        assertNotNull(response);
        assertEquals(ResponseEnum.NOT_FOUND.getHttpStatusCode(), response.getStatusCode());
        assertFalse(response.getIsSuccess());
        assertEquals(MessageEnum.EMPTY_LIST.getMessage(), response.getMessage());
        assertNull(response.getData());
        assertFalse(response.getShowNotification());

        verify(authorRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "firstName"));
        verify(authorMapper, times(1)).toResponseDTOList(Collections.emptyList());
    }
}