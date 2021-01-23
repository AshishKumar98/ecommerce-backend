package com.ecom.bookservice.service;

import com.ecom.bookservice.enitity.Book;
import com.ecom.bookservice.rest.model.BookPage;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {

    Book findByID(int Id);
    Book save(Book book);
    boolean deleteByID(int Id);
    List<Book> findAll();
}
