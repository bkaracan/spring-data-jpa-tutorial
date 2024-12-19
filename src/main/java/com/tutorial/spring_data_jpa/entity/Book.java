package com.tutorial.spring_data_jpa.entity;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
            name = "BOOK_ID_GENERATOR",
            allocationSize = 1,
            sequenceName = "BOOK_ID_GEN")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_ID_GENERATOR")
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "book_name", nullable = false, length = 255)
    private String bookName;

    @Column(name = "isbn", nullable = false, unique = true, length = 20)
    private String isbn;

    @Column(name = "publish_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date publishDate;

    @Column(nullable = false)
    private Integer page;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String summary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    public Book() {
    }

    public Book(Long id, String bookName, String isbn, Date publishDate, Integer page, String summary, Author author) {
        this.id = id;
        this.bookName = bookName;
        this.isbn = isbn;
        this.publishDate = publishDate;
        this.page = page;
        this.summary = summary;
        this.author = author;
    }

    public Book(String bookName, String isbn, Date publishDate, Integer page, String summary, Author author) {
        this.bookName = bookName;
        this.isbn = isbn;
        this.publishDate = publishDate;
        this.page = page;
        this.summary = summary;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}