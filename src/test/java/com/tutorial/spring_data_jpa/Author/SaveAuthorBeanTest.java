package com.tutorial.spring_data_jpa.Author;

import com.tutorial.spring_data_jpa.bean.Author.SaveAuthorBean;
import com.tutorial.spring_data_jpa.dto.AuthorRequestDTO;
import com.tutorial.spring_data_jpa.dto.AuthorResponseDTO;
import com.tutorial.spring_data_jpa.entity.Author;
import com.tutorial.spring_data_jpa.enums.MessageEnum;
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

class SaveAuthorBeanTest {

    @InjectMocks private SaveAuthorBean saveAuthorBean;

    @Mock private AuthorRepository authorRepository;
    @Mock private AuthorMapper authorMapper;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveAuthor_Success() {
        // Arrange
        AuthorRequestDTO requestDTO = new AuthorRequestDTO();
        requestDTO.setFirstName("John");
        requestDTO.setLastName("Doe");
        requestDTO.setBirthDate(new Date());
        requestDTO.setBiography("Test Biography");

        when(authorRepository.findByFirstNameAndLastName(requestDTO.getFirstName(), requestDTO.getLastName()))
                .thenReturn(Optional.empty());

        Author savedAuthor =
                new Author("John", "Doe", requestDTO.getBirthDate(), null, requestDTO.getBiography());
        savedAuthor.setId(1L); // Assuming there is an ID field

        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);
        when(authorMapper.toResponseDTO(any(Author.class))).thenReturn(
                AuthorResponseDTO.builder()
                        .id(1L)
                        .firstName("John")
                        .lastName("Doe")
                        .birthDate(requestDTO.getBirthDate())
                        .biography(requestDTO.getBiography())
                        .build()
        );
        when(authorMapper.toEntity(any(AuthorRequestDTO.class))).thenReturn(savedAuthor);


        // Act
        ResponsePayload<AuthorResponseDTO> response = saveAuthorBean.save(requestDTO);

        // Assert
        assertNotNull(response);
        assertTrue(response.getIsSuccess());
        assertEquals(MessageEnum.SAVE_SUCCESS.getMessage(), response.getMessage());
        assertNotNull(response.getData());
        assertEquals(savedAuthor.getId(), response.getData().getId());
        assertEquals(savedAuthor.getFirstName(), response.getData().getFirstName());
        assertEquals(savedAuthor.getLastName(), response.getData().getLastName());
        assertEquals(savedAuthor.getBirthDate(), response.getData().getBirthDate());
        assertEquals(savedAuthor.getBiography(), response.getData().getBiography());

        verify(authorRepository, times(1))
                .findByFirstNameAndLastName(requestDTO.getFirstName(), requestDTO.getLastName());
        verify(authorRepository, times(1)).save(any(Author.class));
        verify(authorMapper, times(1)).toResponseDTO(any(Author.class));
        verify(authorMapper, times(1)).toEntity(any(AuthorRequestDTO.class));

    }

    @Test
    void save_AlreadyExists() {
        // Arrange
        AuthorRequestDTO authorRequestDTO = new AuthorRequestDTO();
        authorRequestDTO.setFirstName("John");
        authorRequestDTO.setLastName("Doe");
        authorRequestDTO.setBirthDate(new Date());
        authorRequestDTO.setBiography("Test Biography");

        Author author =
                new Author("John", "Doe", authorRequestDTO.getBirthDate(), null, authorRequestDTO.getBiography());
        author.setId(1L);
        when(
                authorRepository.findByFirstNameAndLastName(
                        authorRequestDTO.getFirstName(), authorRequestDTO.getLastName()))
                .thenReturn(Optional.of(author));


        // Act
        ResponsePayload<AuthorResponseDTO> response = saveAuthorBean.save(authorRequestDTO);

        // Assert
        assertNotNull(response);
        assertFalse(response.getIsSuccess());
        assertEquals(MessageEnum.AUTHOR_ALREADY_EXISTS.getMessage(), response.getMessage());

        verify(authorRepository, times(1))
                .findByFirstNameAndLastName(
                        authorRequestDTO.getFirstName(), authorRequestDTO.getLastName());
        verify(authorRepository, times(0)).save(any(Author.class));
    }
}