package com.tutorial.spring_data_jpa.Book;

import com.tutorial.spring_data_jpa.bean.Book.ListBookBean;
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
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListBookBeanTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private ListBookBean listBookBean;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks_Success() {
        // Arrange
        Date publishDate = new Date();
        Author author = new Author(1L, "John", "Doe", new Date(), null, "Test Biography");

        List<Book> bookEntities = Arrays.asList(
                new Book(1L, "Book 1","ISBN-1", publishDate, 200, "Summary 1",author),
                new Book(2L, "Book 2","ISBN-2", publishDate, 300, "Summary 2",author)
        );
        List<BookResponseDTO> bookDTOs = Arrays.asList(
                BookResponseDTO.builder().id(1L).bookName("Book 1").isbn("ISBN-1").publishDate(publishDate).page(200).summary("Summary 1").authorFirstName("John").authorLastName("Doe").build(),
                BookResponseDTO.builder().id(2L).bookName("Book 2").isbn("ISBN-2").publishDate(publishDate).page(300).summary("Summary 2").authorFirstName("John").authorLastName("Doe").build()
        );

        when(bookRepository.findAll(Sort.by(Sort.Direction.ASC, "bookName"))).thenReturn(bookEntities);
        when(bookMapper.toResponseDTOList(bookEntities)).thenReturn(bookDTOs);

        // Act
        ResponsePayload<List<BookResponseDTO>> response = listBookBean.getAllBooks();

        // Assert
        assertNotNull(response);
        assertEquals(ResponseEnum.OK.getHttpStatusCode(), response.getStatusCode());
        assertTrue(response.getIsSuccess());
        assertEquals(MessageEnum.FETCH_SUCCESS.getMessage(), response.getMessage());
        assertEquals(bookDTOs.size(), response.getData().size());
        assertEquals(bookDTOs.get(0).getId(), response.getData().get(0).getId());
        assertEquals(bookDTOs.get(1).getId(), response.getData().get(1).getId());
        assertFalse(response.getShowNotification());

        verify(bookRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "bookName"));
        verify(bookMapper, times(1)).toResponseDTOList(bookEntities);
    }

    @Test
    void testGetAllBooks_EmptyList() {
        // Arrange
        when(bookRepository.findAll(Sort.by(Sort.Direction.ASC, "bookName"))).thenReturn(Collections.emptyList());
        when(bookMapper.toResponseDTOList(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Act
        ResponsePayload<List<BookResponseDTO>> response = listBookBean.getAllBooks();

        // Assert
        assertNotNull(response);
        assertEquals(ResponseEnum.NOT_FOUND.getHttpStatusCode(), response.getStatusCode());
        assertFalse(response.getIsSuccess());
        assertEquals(MessageEnum.EMPTY_LIST.getMessage(), response.getMessage());
        assertNull(response.getData());
        assertFalse(response.getShowNotification());

        verify(bookRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "bookName"));
        verify(bookMapper, times(1)).toResponseDTOList(Collections.emptyList());
    }
    @Test
    void testGetBooksByAuthorId_Success() {
        // Arrange
        Long authorId = 1L;
        Date publishDate = new Date();
        Author author = new Author(authorId, "John", "Doe", new Date(), null, "Test Biography");

        List<Book> bookEntities = Arrays.asList(
                new Book(1L, "Book 1","ISBN-1", publishDate, 200, "Summary 1",author),
                new Book(2L, "Book 2","ISBN-2", publishDate, 300, "Summary 2",author)
        );
        List<BookResponseDTO> bookDTOs = Arrays.asList(
                BookResponseDTO.builder().id(1L).bookName("Book 1").isbn("ISBN-1").publishDate(publishDate).page(200).summary("Summary 1").authorFirstName("John").authorLastName("Doe").build(),
                BookResponseDTO.builder().id(2L).bookName("Book 2").isbn("ISBN-2").publishDate(publishDate).page(300).summary("Summary 2").authorFirstName("John").authorLastName("Doe").build()
        );


        when(bookRepository.findByAuthorId(authorId,Sort.by(Sort.Direction.ASC, "bookName"))).thenReturn(bookEntities);
        when(bookMapper.toResponseDTOList(bookEntities)).thenReturn(bookDTOs);

        // Act
        ResponsePayload<List<BookResponseDTO>> response = listBookBean.getBooksByAuthorId(authorId);

        // Assert
        assertNotNull(response);
        assertEquals(ResponseEnum.OK.getHttpStatusCode(), response.getStatusCode());
        assertTrue(response.getIsSuccess());
        assertEquals(MessageEnum.FETCH_SUCCESS.getMessage(), response.getMessage());
        assertEquals(bookDTOs.size(), response.getData().size());
        assertEquals(bookDTOs.get(0).getId(), response.getData().get(0).getId());
        assertEquals(bookDTOs.get(1).getId(), response.getData().get(1).getId());
        assertFalse(response.getShowNotification());

        verify(bookRepository, times(1)).findByAuthorId(authorId,Sort.by(Sort.Direction.ASC, "bookName"));
        verify(bookMapper, times(1)).toResponseDTOList(bookEntities);
    }
    @Test
    void testGetBooksByAuthorId_EmptyList() {
        // Arrange
        Long authorId = 1L;
        when(bookRepository.findByAuthorId(authorId,Sort.by(Sort.Direction.ASC, "bookName"))).thenReturn(Collections.emptyList());
        when(bookMapper.toResponseDTOList(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Act
        ResponsePayload<List<BookResponseDTO>> response = listBookBean.getBooksByAuthorId(authorId);

        // Assert
        assertNotNull(response);
        assertEquals(ResponseEnum.NOT_FOUND.getHttpStatusCode(), response.getStatusCode());
        assertFalse(response.getIsSuccess());
        assertEquals(MessageEnum.EMPTY_LIST.getMessage(), response.getMessage());
        assertNull(response.getData());
        assertFalse(response.getShowNotification());

        verify(bookRepository, times(1)).findByAuthorId(authorId,Sort.by(Sort.Direction.ASC, "bookName"));
        verify(bookMapper, times(1)).toResponseDTOList(Collections.emptyList());
    }
}