package com.ecom.bookservice.service.impl;

import com.ecom.bookservice.enitity.Book;
import com.ecom.bookservice.repository.BookRepos;
import com.ecom.bookservice.repository.BookRepository;
import com.ecom.bookservice.rest.model.BookPage;
import com.ecom.bookservice.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public Book findByID(int Id) {
        return bookRepository.findById(Id);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public boolean deleteByID(int Id) {
        return bookRepository.deleteById(Id);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }



}
