package com.tutorial.spring_data_jpa.entity;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "author")
public class Author implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @SequenceGenerator(
          name = "AUTHOR_ID_GENERATOR",
          allocationSize = 1,
          sequenceName = "AUTHOR_ID_GEN")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTHOR_ID_GENERATOR")
  @Column(unique = true, nullable = false)
  private Long id;

  @Column(name = "first_name", nullable = false, length = 100)
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 100)
  private String lastName;

  @Column(name = "birth_date", nullable = false)
  @Temporal(TemporalType.DATE)
  private Date birthDate;

  @Column(name = "death_date", nullable = true)
  @Temporal(TemporalType.DATE)
  private Date deathDate;

  @Lob
  @Column(columnDefinition = "TEXT")
  private String biography;

  @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
  private List<Book> books;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public Date getDeathDate() {
    return deathDate;
  }

  public void setDeathDate(Date deathDate) {
    this.deathDate = deathDate;
  }

  public String getBiography() {
    return biography;
  }

  public void setBiography(String biography) {
    this.biography = biography;
  }

  public List<Book> getBooks() {
    return books;
  }

  public void setBooks(List<Book> books) {
    this.books = books;
  }

  public Author(Long id, String firstName, String lastName, Date birthDate, Date deathDate, String biography) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = birthDate;
    this.deathDate = deathDate;
    this.biography = biography;
  }

  public Author(String firstName, String lastName, Date birthDate, Date deathDate, String biography) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = birthDate;
    this.deathDate = deathDate;
    this.biography = biography;
  }

  public Author() {}
}