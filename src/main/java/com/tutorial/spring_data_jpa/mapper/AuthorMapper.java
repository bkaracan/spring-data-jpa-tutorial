package com.tutorial.spring_data_jpa.mapper;

import com.tutorial.spring_data_jpa.dto.AuthorRequestDTO;
import com.tutorial.spring_data_jpa.dto.AuthorResponseDTO;
import com.tutorial.spring_data_jpa.entity.Author;
import java.util.List;

public class AuthorMapper {

    
    public static Author toEntity(AuthorRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Author author = new Author();
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        author.setAge(dto.getAge());
        author.setEmail(dto.getEmail());
        return author;
    }

    
    public static List<Author> toEntityList(List<AuthorRequestDTO> dtoList) {
        if (dtoList == null || dtoList.isEmpty()) {
            return List.of();
        }
        return dtoList.stream()
                .map(AuthorMapper::toEntity)
                .toList();
    }

    
    public static AuthorResponseDTO toResponseDTO(Author entity) {
        if (entity == null) {
            return null;
        }
        return AuthorResponseDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .age(entity.getAge())
                .email(entity.getEmail())
                .build();
    }

   
    public static List<AuthorResponseDTO> toResponseDTOList(List<Author> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return List.of();
        }
        return entityList.stream()
                .map(AuthorMapper::toResponseDTO)
                .toList();
    }
}
