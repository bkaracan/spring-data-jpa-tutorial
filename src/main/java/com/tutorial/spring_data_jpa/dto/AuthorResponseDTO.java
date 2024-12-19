package com.tutorial.spring_data_jpa.dto;

import java.util.Date;

public class AuthorResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Date deathDate;
    private String biography;


    private AuthorResponseDTO(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.birthDate = builder.birthDate;
        this.deathDate = builder.deathDate;
        this.biography = builder.biography;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Date getDeathDate() {
        return deathDate;
    }

    public String getBiography() {
        return biography;
    }

    public static class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private Date birthDate;
        private Date deathDate;
        private String biography;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder birthDate(Date birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder deathDate(Date deathDate) {
            this.deathDate = deathDate;
            return this;
        }

        public Builder biography(String biography) {
            this.biography = biography;
            return this;
        }

        public AuthorResponseDTO build() {
            return new AuthorResponseDTO(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}