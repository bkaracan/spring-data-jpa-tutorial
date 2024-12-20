package com.tutorial.spring_data_jpa.controller;

import com.tutorial.spring_data_jpa.dto.BookRequestDTO;
import com.tutorial.spring_data_jpa.dto.BookResponseDTO;
import com.tutorial.spring_data_jpa.payload.ResponsePayload;
import com.tutorial.spring_data_jpa.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/getById", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find book by id", description = "Returns a book as per the id")
    public ResponsePayload<BookResponseDTO> findBookById(@Parameter(description = "Id of the book to be found") @RequestParam("id") Long id) {
        return bookService.findById(id);
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Save a new book", description = "Saves a book to database")
    public ResponsePayload<BookResponseDTO> saveBook(@Valid @RequestBody BookRequestDTO bookRequestDTO) {
        return bookService.save(bookRequestDTO);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update an existing book", description = "Updates an existing book data with related fields")
    public ResponsePayload<BookResponseDTO> updateBook(Long id, @Valid @RequestBody BookRequestDTO bookRequestDTO) {
        return bookService.update(id, bookRequestDTO);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all books data", description = "Retrieve all books from database")
    public ResponsePayload<List<BookResponseDTO>> getAllBooks() {
        return bookService.getAll();
    }

    @DeleteMapping(value = "/deleteById")
    @Operation(summary = "Delete an existing book", description = "Delete an existing author data from database")
    public ResponsePayload<?> deleteBook(Long id) {
        return bookService.delete(id);
    }

    @GetMapping(value = "/getByPage", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get books by page", description = "Retrieve paginated books data")
    public ResponsePayload<List<BookResponseDTO>> getBooksByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
                return bookService.getBooksByPage(page, size);
    }

    @GetMapping(value = "/getBooksByAuthorId", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get books by author id", description = "Retrieve author's book list")
    public ResponsePayload<List<BookResponseDTO>> getBooksByAuthorId(@RequestParam ("authorId") Long authorId) {
        return bookService.getBooksByAuthorId(authorId);
    }

}
