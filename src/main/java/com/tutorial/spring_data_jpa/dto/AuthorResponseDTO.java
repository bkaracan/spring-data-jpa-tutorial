package com.tutorial.spring_data_jpa.dto;

public class AuthorResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;

    private AuthorResponseDTO(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.email = builder.email;
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

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public static class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private Integer age;
        private String email;

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

        public Builder age(Integer age) {
            this.age = age;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
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