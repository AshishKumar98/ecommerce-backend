package com.ecom.bookservice.repository;

import com.ecom.bookservice.enitity.Book;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepos extends PagingAndSortingRepository<Book, Integer> {

}
