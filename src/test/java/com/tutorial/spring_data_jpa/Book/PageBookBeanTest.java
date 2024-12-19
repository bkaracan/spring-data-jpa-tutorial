package com.tutorial.spring_data_jpa.Book;

import com.tutorial.spring_data_jpa.bean.Book.PageBookBean;
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

class PageBookBeanTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private PageBookBean pageBookBean;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBooksByPage_Success() {
        // Arrange
        int page = 0;
        int size = 2;
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


        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = new PageImpl<>(bookEntities, pageable, bookEntities.size());

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.toResponseDTO(any(Book.class)))
                .thenReturn(bookDTOs.get(0), bookDTOs.get(1));


        // Act
        ResponsePayload<List<BookResponseDTO>> response = pageBookBean.getBooksByPage(page, size);

        // Assert
        assertNotNull(response);
        assertEquals(ResponseEnum.OK.getHttpStatusCode(), response.getStatusCode());
        assertTrue(response.getIsSuccess());
        assertEquals(MessageEnum.FETCH_SUCCESS.getMessage(), response.getMessage());
        assertEquals(size, response.getData().size());
        assertEquals(bookDTOs.get(0).getId(), response.getData().get(0).getId());
        assertEquals(bookDTOs.get(1).getId(), response.getData().get(1).getId());


        verify(bookRepository, times(1)).findAll(pageable);
        verify(bookMapper, times(2)).toResponseDTO(any(Book.class));
    }

    @Test
    void testGetBooksByPage_EmptyList() {
        // Arrange
        int page = 0;
        int size = 2;
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> emptyBookPage = new PageImpl<>(Collections.emptyList(),pageable,0);

        when(bookRepository.findAll(pageable)).thenReturn(emptyBookPage);

        // Act
        ResponsePayload<List<BookResponseDTO>> response = pageBookBean.getBooksByPage(page, size);

        // Assert
        assertNotNull(response);
        assertEquals(ResponseEnum.NOT_FOUND.getHttpStatusCode(), response.getStatusCode());
        assertFalse(response.getIsSuccess());
        assertEquals(MessageEnum.EMPTY_LIST.getMessage(), response.getMessage());
        assertNull(response.getData());

        verify(bookRepository, times(1)).findAll(pageable);
        verify(bookMapper, times(0)).toResponseDTO(any(Book.class));
    }

    @Test
    void testGetBooksByAuthorIdByPage_Success() {
        // Arrange
        Long authorId = 1L;
        int page = 0;
        int size = 2;
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

        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = new PageImpl<>(bookEntities, pageable, bookEntities.size());

        when(bookRepository.findByAuthorId(authorId,pageable)).thenReturn(bookPage);
        when(bookMapper.toResponseDTO(any(Book.class)))
                .thenReturn(bookDTOs.get(0), bookDTOs.get(1));


        // Act
        ResponsePayload<List<BookResponseDTO>> response = pageBookBean.getBooksByAuthorIdByPage(authorId,page, size);

        // Assert
        assertNotNull(response);
        assertEquals(ResponseEnum.OK.getHttpStatusCode(), response.getStatusCode());
        assertTrue(response.getIsSuccess());
        assertEquals(MessageEnum.FETCH_SUCCESS.getMessage(), response.getMessage());
        assertEquals(size, response.getData().size());
        assertEquals(bookDTOs.get(0).getId(), response.getData().get(0).getId());
        assertEquals(bookDTOs.get(1).getId(), response.getData().get(1).getId());

        verify(bookRepository, times(1)).findByAuthorId(authorId,pageable);
        verify(bookMapper, times(2)).toResponseDTO(any(Book.class));
    }

    @Test
    void testGetBooksByAuthorIdByPage_EmptyList() {
        // Arrange
        Long authorId = 1L;
        int page = 0;
        int size = 2;
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> emptyBookPage = new PageImpl<>(Collections.emptyList(),pageable,0);


        when(bookRepository.findByAuthorId(authorId,pageable)).thenReturn(emptyBookPage);

        // Act
        ResponsePayload<List<BookResponseDTO>> response = pageBookBean.getBooksByAuthorIdByPage(authorId,page, size);


        // Assert
        assertNotNull(response);
        assertEquals(ResponseEnum.NOT_FOUND.getHttpStatusCode(), response.getStatusCode());
        assertFalse(response.getIsSuccess());
        assertEquals(MessageEnum.EMPTY_LIST.getMessage(), response.getMessage());
        assertNull(response.getData());

        verify(bookRepository, times(1)).findByAuthorId(authorId,pageable);
        verify(bookMapper, times(0)).toResponseDTO(any(Book.class));
    }
}