package com.tutorial.spring_data_jpa.dto;

import java.util.Date;

public class BookResponseDTO {

    private Long id;
    private String bookName;
    private String isbn;
    private Date publishDate;
    private Integer page;
    private String summary;

    private String authorFirstName;
    private String authorLastName;


    private BookResponseDTO(Builder builder) {
        this.id = builder.id;
        this.bookName = builder.bookName;
        this.isbn = builder.isbn;
        this.publishDate = builder.publishDate;
        this.page = builder.page;
        this.summary = builder.summary;
        this.authorFirstName = builder.authorFirstName;
        this.authorLastName = builder.authorLastName;
    }

    public Long getId() {
        return id;
    }

    public String getBookName() {
        return bookName;
    }

    public String getIsbn() {
        return isbn;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public Integer getPage() {
        return page;
    }

    public String getSummary() {
        return summary;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public static class Builder {
        private Long id;
        private String bookName;
        private String isbn;
        private Date publishDate;
        private Integer page;
        private String summary;
        private String authorFirstName;
        private String authorLastName;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder bookName(String bookName) {
            this.bookName = bookName;
            return this;
        }

        public Builder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public Builder publishDate(Date publishDate) {
            this.publishDate = publishDate;
            return this;
        }

        public Builder page(Integer page) {
            this.page = page;
            return this;
        }

        public Builder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public Builder authorFirstName(String authorFirstName) {
            this.authorFirstName = authorFirstName;
            return this;
        }
        public Builder authorLastName(String authorLastName) {
            this.authorLastName = authorLastName;
            return this;
        }

        public BookResponseDTO build() {
            return new BookResponseDTO(this);
        }
    }
    public static Builder builder(){
        return new Builder();
    }

}