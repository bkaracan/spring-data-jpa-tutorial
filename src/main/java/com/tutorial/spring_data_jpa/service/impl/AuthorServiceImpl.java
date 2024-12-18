package com.tutorial.spring_data_jpa.service.impl;

import com.tutorial.spring_data_jpa.bean.FindAuthorBean;
import com.tutorial.spring_data_jpa.bean.SaveAuthorBean;
import com.tutorial.spring_data_jpa.dto.AuthorRequestDTO;
import com.tutorial.spring_data_jpa.dto.AuthorResponseDTO;
import com.tutorial.spring_data_jpa.payload.ResponsePayload;
import com.tutorial.spring_data_jpa.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

  private final FindAuthorBean findAuthorBean;

  private final SaveAuthorBean saveAuthorBean;

  @Autowired
  public AuthorServiceImpl(FindAuthorBean findAuthorBean, SaveAuthorBean saveAuthorBean) {
    this.findAuthorBean = findAuthorBean;
    this.saveAuthorBean = saveAuthorBean;
  }

  @Override
  public ResponsePayload<AuthorResponseDTO> findById(Long id) {
    return findAuthorBean.findById(id);
  }

  @Override
  public ResponsePayload<AuthorResponseDTO> save(AuthorRequestDTO authorRequestDTO) {
    return saveAuthorBean.save(authorRequestDTO);
  }
}
