package com.tutorial.spring_data_jpa.mapper;

import com.tutorial.spring_data_jpa.dto.AuthorRequestDTO;
import com.tutorial.spring_data_jpa.dto.AuthorResponseDTO;
import com.tutorial.spring_data_jpa.entity.Author;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorMapper {


    public Author toEntity(AuthorRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Author author = new Author();
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        author.setBirthDate(dto.getBirthDate());
        author.setDeathDate(dto.getDeathDate());
        author.setBiography(dto.getBiography());
        return author;
    }


    public List<Author> toEntityList(List<AuthorRequestDTO> dtoList) {
        if (dtoList == null || dtoList.isEmpty()) {
            return List.of();
        }
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }


    public AuthorResponseDTO toResponseDTO(Author entity) {
        if (entity == null) {
            return null;
        }
        return AuthorResponseDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .birthDate(entity.getBirthDate())
                .deathDate(entity.getDeathDate())
                .biography(entity.getBiography())
                .build();
    }


    public List<AuthorResponseDTO> toResponseDTOList(List<Author> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return List.of();
        }
        return entityList.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}