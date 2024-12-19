package com.tutorial.spring_data_jpa.Author;

import com.tutorial.spring_data_jpa.bean.Author.UpdateAuthorBean;
import com.tutorial.spring_data_jpa.dto.AuthorRequestDTO;
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

class UpdateAuthorBeanTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private UpdateAuthorBean updateAuthorBean;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateAuthor_Success() {
        // Arrange
        Long authorId = 1L;
        Date birthDate = new Date();

        AuthorRequestDTO requestDTO = new AuthorRequestDTO();
        requestDTO.setFirstName("UpdatedJohn");
        requestDTO.setLastName("UpdatedDoe");
        requestDTO.setBirthDate(birthDate);
        requestDTO.setBiography("Updated Biography");

        Author existingAuthor =
                new Author(authorId, "John", "Doe", birthDate, null, "Test Biography");

        Author updatedAuthor =
                new Author(authorId, "UpdatedJohn", "UpdatedDoe", birthDate, null, "Updated Biography");


        AuthorResponseDTO authorResponseDTO = AuthorResponseDTO.builder()
                .id(authorId)
                .firstName("UpdatedJohn")
                .lastName("UpdatedDoe")
                .birthDate(birthDate)
                .biography("Updated Biography")
                .build();

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));
        when(authorRepository.save(any(Author.class))).thenReturn(updatedAuthor);
        when(authorMapper.toResponseDTO(any(Author.class))).thenReturn(authorResponseDTO);


        // Act
        ResponsePayload<AuthorResponseDTO> response = updateAuthorBean.updateById(authorId, requestDTO);

        // Assert
        assertNotNull(response);
        assertTrue(response.getIsSuccess());
        assertEquals(MessageEnum.UPDATE_SUCCESS.getMessage(), response.getMessage());
        assertEquals(authorResponseDTO.getId(), response.getData().getId());
        assertEquals(authorResponseDTO.getFirstName(), response.getData().getFirstName());
        assertEquals(authorResponseDTO.getLastName(), response.getData().getLastName());
        assertEquals(authorResponseDTO.getBirthDate(), response.getData().getBirthDate());
        assertEquals(authorResponseDTO.getBiography(), response.getData().getBiography());

        verify(authorRepository, times(1)).findById(authorId);
        verify(authorRepository, times(1)).save(any(Author.class));
        verify(authorMapper, times(1)).toResponseDTO(any(Author.class));
    }

    @Test
    void testUpdateAuthor_NotFound() {
        // Arrange
        Long authorId = 1L;
        AuthorRequestDTO requestDTO = new AuthorRequestDTO();
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());


        // Act
        ResponsePayload<AuthorResponseDTO> response = updateAuthorBean.updateById(authorId, requestDTO);

        // Assert
        assertNotNull(response);
        assertFalse(response.getIsSuccess());
        assertEquals(ResponseEnum.NOT_FOUND.getHttpStatusCode(), response.getStatusCode());
        assertEquals(MessageEnum.NOT_FOUND.getMessage(), response.getMessage());
        assertNull(response.getData());

        verify(authorRepository, times(1)).findById(authorId);
        verify(authorRepository, times(0)).save(any(Author.class));
    }
}