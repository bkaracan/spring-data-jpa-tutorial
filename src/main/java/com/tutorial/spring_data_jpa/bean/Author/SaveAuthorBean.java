package com.tutorial.spring_data_jpa.bean.Author;

import com.tutorial.spring_data_jpa.dto.AuthorRequestDTO;
import com.tutorial.spring_data_jpa.dto.AuthorResponseDTO;
import com.tutorial.spring_data_jpa.entity.Author;
import com.tutorial.spring_data_jpa.enums.MessageEnum;
import com.tutorial.spring_data_jpa.enums.ResponseEnum;
import com.tutorial.spring_data_jpa.mapper.AuthorMapper;
import com.tutorial.spring_data_jpa.payload.AbstractResponsePayload;
import com.tutorial.spring_data_jpa.payload.ResponsePayload;
import com.tutorial.spring_data_jpa.repository.AuthorRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SaveAuthorBean extends AbstractResponsePayload {

  private final AuthorRepository authorRepository;

  private final AuthorMapper authorMapper;

  @Autowired
  public SaveAuthorBean(AuthorRepository authorRepository, AuthorMapper authorMapper) {
    this.authorRepository = authorRepository;
      this.authorMapper = authorMapper;
  }

  @Transactional
  public ResponsePayload<AuthorResponseDTO> save(AuthorRequestDTO requestDTO) {

    Optional<Author> existingAuthor =
            authorRepository.findByFirstNameAndLastName(
                    requestDTO.getFirstName(), requestDTO.getLastName());

    if (existingAuthor.isPresent()) {
      return createErrorResponse(
              ResponseEnum.BAD_REQUEST, MessageEnum.AUTHOR_ALREADY_EXISTS.getMessage());
    }

    Author author = authorMapper.toEntity(requestDTO);
    Author savedAuthor = authorRepository.save(author);
    return createSuccessResponse(
            MessageEnum.SAVE_SUCCESS.getMessage(), authorMapper.toResponseDTO(savedAuthor));
  }
}