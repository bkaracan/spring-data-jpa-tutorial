package com.tutorial.spring_data_jpa.service.impl;

import com.tutorial.spring_data_jpa.bean.Author.*;
import com.tutorial.spring_data_jpa.dto.AuthorRequestDTO;
import com.tutorial.spring_data_jpa.dto.AuthorResponseDTO;
import com.tutorial.spring_data_jpa.payload.ResponsePayload;
import com.tutorial.spring_data_jpa.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

  private final FindAuthorBean findAuthorBean;

  private final SaveAuthorBean saveAuthorBean;

  private final UpdateAuthorBean updateAuthorBean;

  private final ListAuthorBean listAuthorBean;

  private final DeleteAuthorBean deleteAuthorBean;

  private final PageAuthorBean pageAuthorBean;

  @Autowired
  public AuthorServiceImpl(FindAuthorBean findAuthorBean, SaveAuthorBean saveAuthorBean, UpdateAuthorBean updateAuthorBean, ListAuthorBean listAuthorBean, DeleteAuthorBean deleteAuthorBean, PageAuthorBean pageAuthorBean) {
    this.findAuthorBean = findAuthorBean;
    this.saveAuthorBean = saveAuthorBean;
      this.updateAuthorBean = updateAuthorBean;
      this.listAuthorBean = listAuthorBean;
      this.deleteAuthorBean = deleteAuthorBean;
      this.pageAuthorBean = pageAuthorBean;
  }

  @Override
  public ResponsePayload<AuthorResponseDTO> findById(Long id) {
    return findAuthorBean.findById(id);
  }

  @Override
  public ResponsePayload<AuthorResponseDTO> save(AuthorRequestDTO authorRequestDTO) {
    return saveAuthorBean.save(authorRequestDTO);
  }

  @Override
  public ResponsePayload<AuthorResponseDTO> update(Long id, AuthorRequestDTO authorRequestDTO) {
    return updateAuthorBean.updateById(id, authorRequestDTO);
  }

  @Override
  public ResponsePayload<List<AuthorResponseDTO>> getAll() {
    return listAuthorBean.getAllAuthors();
  }

  @Override
  public ResponsePayload<?> delete(Long id) {
    return deleteAuthorBean.deleteById(id);
  }

  @Override
  public ResponsePayload<List<AuthorResponseDTO>> getAuthorsByPage(Integer page, Integer size) {
    return pageAuthorBean.getAuthorsByPage(page, size);
  }
}
