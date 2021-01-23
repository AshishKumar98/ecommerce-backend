package com.ecom.bookservice.repository;

import com.ecom.bookservice.enitity.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Slf4j
@Transactional
@Repository
public class BookRepository {

    @Autowired
    EntityManager em;

    public Book findById(int id) {
        Book book = em.find(Book.class, id);
        return book;
    }

    public Book save(Book book) {
        if (findById(book.getBookID()) == null){
            em.persist(book);
        } else {
            em.merge(book);
        }
        return book;
    }

    public boolean deleteById(int id) {
        em.remove(findById(id));
        return true;
    }

    public List<Book> findAll() {
        TypedQuery<Book> query = em.createNamedQuery("query_get_all_books", Book.class);

        List<Book> resultList = query.getResultList();
        log.info("Book List size: {}",resultList.size());
        return resultList;

    }

}
