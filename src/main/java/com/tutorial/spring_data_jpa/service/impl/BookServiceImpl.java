package com.tutorial.spring_data_jpa.service.impl;

import com.tutorial.spring_data_jpa.bean.Book.*;
import com.tutorial.spring_data_jpa.dto.BookRequestDTO;
import com.tutorial.spring_data_jpa.dto.BookResponseDTO;
import com.tutorial.spring_data_jpa.payload.ResponsePayload;
import com.tutorial.spring_data_jpa.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final FindBookBean findBookBean;
    private final SaveBookBean saveBookBean;
    private final UpdateBookBean updateBookBean;
    private final ListBookBean listBookBean;
    private final DeleteBookBean deleteBookBean;
    private final PageBookBean pageBookBean;

    @Autowired
    public BookServiceImpl(FindBookBean findBookBean, SaveBookBean saveBookBean, UpdateBookBean updateBookBean, ListBookBean listBookBean, DeleteBookBean deleteBookBean, PageBookBean pageBookBean) {
        this.findBookBean = findBookBean;
        this.saveBookBean = saveBookBean;
        this.updateBookBean = updateBookBean;
        this.listBookBean = listBookBean;
        this.deleteBookBean = deleteBookBean;
        this.pageBookBean = pageBookBean;
    }

    @Override
    public ResponsePayload<BookResponseDTO> findById(Long id) {
        return findBookBean.findById(id);
    }

    @Override
    public ResponsePayload<BookResponseDTO> save(BookRequestDTO bookRequestDTO) {
        return saveBookBean.save(bookRequestDTO);
    }

    @Override
    public ResponsePayload<BookResponseDTO> update(Long id, BookRequestDTO bookRequestDTO) {
        return updateBookBean.updateById(id, bookRequestDTO);
    }

    @Override
    public ResponsePayload<List<BookResponseDTO>> getAll() {
        return listBookBean.getAllBooks();
    }

    @Override
    public ResponsePayload<?> delete(Long id) {
        return deleteBookBean.deleteById(id);
    }

    @Override
    public ResponsePayload<List<BookResponseDTO>> getBooksByPage(int page, int size) {
        return pageBookBean.getBooksByPage(page, size);
    }

    @Override
    public ResponsePayload<List<BookResponseDTO>> getBooksByAuthorId(Long authorId) {
        return listBookBean.getBooksByAuthorId(authorId);
    }
}
