package com.ecom.bookservice.repository;

import com.ecom.bookservice.enitity.Book;
import com.ecom.bookservice.rest.model.BookPage;
import com.ecom.bookservice.rest.model.BookSearchCriteria;
import com.ecom.bookservice.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class BookCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public BookCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Book> findByFilter(BookPage bookPage,
                                   BookSearchCriteria bookSearchCriteria) {
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> bookRoot = criteriaQuery.from(Book.class);
        Predicate predicate = getPredicate(bookSearchCriteria, bookRoot);
        criteriaQuery.where(predicate);
        setOrder(bookPage, criteriaQuery, bookRoot);
        TypedQuery<Book> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(bookPage.getPageNumber() * bookPage.getPageSize());
        typedQuery.setMaxResults(bookPage.getPageSize());

        Pageable pageable = getPagable(bookPage);

        long booksCount = getBooksCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, booksCount);
    }

    private Predicate getPredicate(BookSearchCriteria bookSearchCriteria,
                                   Root<Book> bookRoot) {
        List<Predicate> predicateList = new ArrayList<>();
        if (Objects.nonNull(bookSearchCriteria.getTitle())) {
            predicateList.add(
                    criteriaBuilder.like(bookRoot.get("title"),
                            "%" + bookSearchCriteria.getTitle() + "%")
            );
        }

        if (Objects.nonNull(bookSearchCriteria.getTitle())) {
            predicateList.add(
                    criteriaBuilder.like(bookRoot.get("languageCode"),
                            "%" + bookSearchCriteria.getLanguageCode() + "%")
            );
        }
        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }

    private void setOrder(BookPage bookPage,
                          CriteriaQuery<Book> criteriaQuery,
                          Root<Book> bookRoot) {
        if (bookPage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(bookRoot.get(bookPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(bookRoot.get(bookPage.getSortBy())));
        }
    }

    private Pageable getPagable(BookPage bookPage) {
        Sort sort = Sort.by(bookPage.getSortDirection(), bookPage.getSortBy());
        return PageRequest.of(bookPage.getPageNumber(), bookPage.getPageSize(), sort);
    }

    private long getBooksCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Book> countRoot = countQuery.from(Book.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();

    }






}
