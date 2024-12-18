package com.tutorial.spring_data_jpa.bean;

import com.tutorial.spring_data_jpa.dto.AuthorRequestDTO;
import com.tutorial.spring_data_jpa.dto.AuthorResponseDTO;
import com.tutorial.spring_data_jpa.entity.Author;
import com.tutorial.spring_data_jpa.enums.MessageEnum;
import com.tutorial.spring_data_jpa.enums.ResponseEnum;
import com.tutorial.spring_data_jpa.mapper.AuthorMapper;
import com.tutorial.spring_data_jpa.payload.AbstractResponsePayload;
import com.tutorial.spring_data_jpa.payload.ResponsePayload;
import com.tutorial.spring_data_jpa.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class UpdateAuthorBean extends AbstractResponsePayload {

    private final AuthorRepository authorRepository;

    @Autowired
    public UpdateAuthorBean(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public ResponsePayload<AuthorResponseDTO> updateById(Long id, AuthorRequestDTO requestDTO) {
        Optional<Author> existingAuthorOpt = authorRepository.findById(id);

        if (existingAuthorOpt.isEmpty()) {
            return createErrorResponse(ResponseEnum.NOT_FOUND, MessageEnum.NOT_FOUND, true);
        }

        Author existingAuthor = existingAuthorOpt.get();

        existingAuthor.setFirstName(requestDTO.getFirstName());
        existingAuthor.setLastName(requestDTO.getLastName());
        existingAuthor.setAge(requestDTO.getAge());
        existingAuthor.setEmail(requestDTO.getEmail());

        Author updatedAuthor = authorRepository.save(existingAuthor);

        AuthorResponseDTO responseDTO = AuthorMapper.toResponseDTO(updatedAuthor);
        return createSuccessResponse(MessageEnum.UPDATE_SUCCESS.getMessage(), responseDTO);
    }
}
