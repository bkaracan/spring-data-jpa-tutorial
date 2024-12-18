package com.tutorial.spring_data_jpa.controller;

import com.tutorial.spring_data_jpa.dto.AuthorRequestDTO;
import com.tutorial.spring_data_jpa.dto.AuthorResponseDTO;
import com.tutorial.spring_data_jpa.payload.ResponsePayload;
import com.tutorial.spring_data_jpa.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/author")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(value = "/getById",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find author by id", description = "Returns an author as per the id")
    public ResponsePayload<AuthorResponseDTO> getAuthorById(@Parameter(description = "Id of the author to be found") @RequestParam("id") Long id) {
        return authorService.findById(id);
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Save a new author", description = "Saves an author to database")
    public ResponsePayload<AuthorResponseDTO> saveAuthor(@Valid @RequestBody AuthorRequestDTO authorRequestDTO) {
        return authorService.save(authorRequestDTO);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update an existing author", description = "Updates an existing author data with related fields")
    public ResponsePayload<AuthorResponseDTO> updateAuthor(Long id, @Valid @RequestBody AuthorRequestDTO authorRequestDTO) {
        return authorService.update(id, authorRequestDTO);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all authors data", description = "Retrieve all authors data from database")
    public ResponsePayload<List<AuthorResponseDTO>> getAllAuthors() {
        return authorService.getAll();
    }

    @DeleteMapping(value = "/deleteById")
    @Operation(summary = "Delete an existing author", description = "Delete an existing author data from database")
    public ResponsePayload<?> deleteAuthor(Long id) {
        return authorService.delete(id);
    }

    @GetMapping(value = "/getByPage", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get authors by page", description = "Retrieve paginated authors data")
    public ResponsePayload<List<AuthorResponseDTO>> getAuthorsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return authorService.getAuthorsByPage(page, size);
    }
}
