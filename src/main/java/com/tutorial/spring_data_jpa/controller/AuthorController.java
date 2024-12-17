package com.tutorial.spring_data_jpa.controller;

import com.tutorial.spring_data_jpa.dto.AuthorResponseDTO;
import com.tutorial.spring_data_jpa.payload.ResponsePayload;
import com.tutorial.spring_data_jpa.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - Author with the id was not found")
    })
    public ResponsePayload<AuthorResponseDTO> getAuthorById(@Parameter(description = "Id of the author to be found") @RequestParam("id") Long id) {
        return authorService.findById(id);
    }
}
