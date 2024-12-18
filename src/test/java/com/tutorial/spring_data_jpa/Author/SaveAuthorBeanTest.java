package com.tutorial.spring_data_jpa.Author;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.tutorial.spring_data_jpa.bean.SaveAuthorBean;
import com.tutorial.spring_data_jpa.dto.AuthorRequestDTO;
import com.tutorial.spring_data_jpa.dto.AuthorResponseDTO;
import com.tutorial.spring_data_jpa.entity.Author;
import com.tutorial.spring_data_jpa.enums.MessageEnum;
import com.tutorial.spring_data_jpa.exception.AuthorAlreadyExistsException;
import com.tutorial.spring_data_jpa.payload.ResponsePayload;
import com.tutorial.spring_data_jpa.repository.AuthorRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SaveAuthorBeanTest {

    @InjectMocks
    private SaveAuthorBean saveAuthorBean;

    @Mock
    private AuthorRepository authorRepository;

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
        requestDTO.setAge(30);
        requestDTO.setEmail("john.doe@example.com");

        when(authorRepository.findByEmail(requestDTO.getEmail())).thenReturn(Optional.empty());

        Author savedAuthor = new Author("John", "Doe", 30, "john.doe@example.com");
        savedAuthor.setId(1L); // Assuming there is an ID field

        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);

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
        assertEquals(savedAuthor.getAge(), response.getData().getAge());
        assertEquals(savedAuthor.getEmail(), response.getData().getEmail());

        verify(authorRepository, times(1)).findByEmail(requestDTO.getEmail());
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void save_AlreadyExists() {
        // Arrange
        AuthorRequestDTO authorRequestDTO = new AuthorRequestDTO();
        authorRequestDTO.setFirstName("John");
        authorRequestDTO.setLastName("Doe");
        authorRequestDTO.setAge(30);
        authorRequestDTO.setEmail("john.doe@example.com");

        Author author = new Author("John", "Doe", 30, "john.doe@example.com");
        author.setId(1L);
        when(authorRepository.findByEmail(authorRequestDTO.getEmail())).thenReturn(Optional.of(author));
        // Act & Assert
        AuthorAlreadyExistsException exception = assertThrows(AuthorAlreadyExistsException.class, () -> {
            saveAuthorBean.save(authorRequestDTO);
        });
        assertEquals(MessageEnum.AUTHOR_ALREADY_EXISTS.getMessage(), exception.getReason());


        verify(authorRepository, times(1)).findByEmail(authorRequestDTO.getEmail());
        verify(authorRepository, times(0)).save(any(Author.class));
    }
}
