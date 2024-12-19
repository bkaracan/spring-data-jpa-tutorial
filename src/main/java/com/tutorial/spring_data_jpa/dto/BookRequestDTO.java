package com.tutorial.spring_data_jpa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.util.Date;

public class BookRequestDTO {

    @NotBlank(message = "Book name cannot be blank!")
    @Size(max = 255, message = "Book name cannot exceed 255 characters!")
    @Schema(description = "Book's name", example = "The Interpretation of Dreams")
    private String bookName;

    @NotBlank(message = "ISBN cannot be blank!")
    @Size(max = 20, message = "ISBN cannot exceed 20 characters!")
    @Schema(description = "Book's ISBN", example = "978-3-16-148410-0")
    private String isbn;

    @NotNull(message = "Publish date is required")
    @PastOrPresent(message = "Publish date must be in the past or present")
    @Schema(description = "Book's publish date", example = "1900-11-14")
    private Date publishDate;

    @NotNull(message = "Page count is required")
    @Min(value = 1, message = "Page count must be at least 1")
    @Schema(description = "Book's page count", example = "600")
    private Integer page;

    @Schema(description = "Book's summary", example = "The Interpretation of Dreams is a book by Sigmund Freud.")
    private String summary;

    @NotNull(message = "Author ID cannot be null!")
    @Schema(description = "Book's author id", example = "1")
    private Long authorId;


    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}