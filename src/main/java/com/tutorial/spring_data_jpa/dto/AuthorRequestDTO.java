package com.tutorial.spring_data_jpa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.util.Date;

public class AuthorRequestDTO {

    @NotBlank(message = "First name cannot be blank!")
    @Size(max = 100, message = "First name cannot exceed 100 characters!")
    @Pattern(
            regexp = "^[\\p{L}]+(?:\\s[\\p{L}]+)*$",
            message = "First name must contain only letters from any alphabet!")
    @Schema(description = "Author's first name", example = "Burak")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank!")
    @Size(max = 100, message = "Last name cannot exceed 100 characters!")
    @Pattern(
            regexp = "^[\\p{L}]+(?:\\s[\\p{L}]+)*$",
            message = "Last name must contain only letters from any alphabet!")
    @Schema(description = "Author's last name", example = "Karacan")
    private String lastName;

    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    @Schema(description = "Author's birth date", example = "1990-01-15")
    private Date birthDate;

    @PastOrPresent(message = "Death date must be in the past or present")
    @Schema(description = "Author's death date", example = "2023-01-15")
    private Date deathDate;

    @Schema(description = "Author's biography", example = "Burak Karacan, ...")
    private String biography;

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
}