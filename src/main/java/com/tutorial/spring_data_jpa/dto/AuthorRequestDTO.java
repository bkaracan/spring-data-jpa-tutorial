package com.tutorial.spring_data_jpa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public class AuthorRequestDTO {

    @NotBlank(message = "First name cannot be blank!")
    @Size(max = 100, message = "First name cannot exceed 100 characters!")
    @Pattern(regexp = "^[\\p{L}]+(?:\\s[\\p{L}]+)*$", message = "First name must contain only letters from any alphabet!")
    @Schema(description = "Author's first name", example = "Burak")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank!")
    @Size(max = 100, message = "Last name cannot exceed 100 characters!")
    @Pattern(regexp = "^[\\p{L}]+(?:\\s[\\p{L}]+)*$", message = "Last name must contain only letters from any alphabet!")
    @Schema(description = "Author's last name", example = "Karacan")
    private String lastName;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Age must be at least 18!")
    @Max(value = 100, message = "Age must be less than or equal to 100!")
    @Schema(description = "Author's age", example = "30")
    private Integer age;

    @NotBlank(message = "Email cannot be blank!")
    @Email(message = "Email should be a valid format!")
    @Schema(description = "Author's email address", example = "burak.karacan@example.com")
    private String email;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
