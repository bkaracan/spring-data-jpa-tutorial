package com.tutorial.spring_data_jpa.service.impl;

import com.tutorial.spring_data_jpa.bean.FindAuthorBean;
import com.tutorial.spring_data_jpa.dto.AuthorResponseDTO;
import com.tutorial.spring_data_jpa.payload.ResponsePayload;
import com.tutorial.spring_data_jpa.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final FindAuthorBean findAuthorBean;

    @Autowired
    public AuthorServiceImpl(FindAuthorBean findAuthorBean) {
        this.findAuthorBean = findAuthorBean;
    }

    @Override
    public ResponsePayload<AuthorResponseDTO> findById(Long id) {
        return findAuthorBean.findById(id);
    }
}
