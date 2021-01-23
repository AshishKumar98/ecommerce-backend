package com.ecom.bookservice.service.impl;

import com.ecom.bookservice.enitity.Book;
import com.ecom.bookservice.repository.BookCriteriaRepository;
import com.ecom.bookservice.repository.BookRepos;
import com.ecom.bookservice.rest.model.BookPage;
import com.ecom.bookservice.rest.model.BookSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BookNewService {

    private final BookRepos bookRepos;
    private final BookCriteriaRepository bookCriteriaRepository;

    public BookNewService(BookRepos bookRepos, BookCriteriaRepository bookCriteriaRepository) {
        this.bookRepos = bookRepos;
        this.bookCriteriaRepository = bookCriteriaRepository;
    }

    public Page<Book> getBooks(BookPage page) {
        Sort sort = Sort.by(page.getSortDirection(), page.getSortBy());
        Pageable pageable = PageRequest.of(page.getPageNumber(),
                page.getPageSize(), sort);
        return bookRepos.findAll(pageable);
    }

    public Book addBook(Book book) {
        return bookRepos.save(book);
    }

    public Page<Book> getBooks(BookPage page, BookSearchCriteria bookSearchCriteria) {
        return bookCriteriaRepository.findByFilter(page, bookSearchCriteria);

    }

}