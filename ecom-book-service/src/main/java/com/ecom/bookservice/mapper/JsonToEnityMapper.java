package com.ecom.bookservice.mapper;

import com.ecom.bookservice.enitity.Book;
import com.ecom.bookservice.rest.model.BookData;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class JsonToEnityMapper {

    public static Book bookDataToEntity(BookData bookData) {
        return Book.builder()
                .bookID(bookData.getBookID())
                .title(bookData.getTitle())
                .authors(bookData.getAuthors())
                .averageRating(bookData.getAverage_rating())
                .isbn(bookData.getIsbn())
                .languageCode(bookData.getLanguage_code())
                .ratingsCount(bookData.getRatings_count())
                .price(bookData.getPrice())
                .build();
    }

    public static BookData entityToBookData(Book book) {
        return BookData.builder()
                .bookID(book.getBookID())
                .title(book.getTitle())
                .authors(book.getAuthors())
                .average_rating(book.getAverageRating())
                .isbn(book.getIsbn())
                .language_code(book.getLanguageCode())
                .ratings_count(book.getRatingsCount())
                .price(book.getPrice())
                .build();
    }

    public static List<BookData> entityToBookData(List<Book> bookList) {
        return bookList.stream()
                       .map(JsonToEnityMapper::entityToBookData)
                       .collect(Collectors.toList());
    }

    public static List<Book> bookDataToEntity(List<BookData> bookList) {
        return bookList.stream()
                .map(JsonToEnityMapper::bookDataToEntity)
                .collect(Collectors.toList());
    }

    public static List<BookData> entityToBookData(Page<Book> bookList) {
        return bookList.stream()
                .map(JsonToEnityMapper::entityToBookData)
                .collect(Collectors.toList());
    }

}
