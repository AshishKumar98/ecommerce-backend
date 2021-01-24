package com.ecom.bookservice.rest.controller.dataimport;

import com.ecom.bookservice.enitity.Book;
import com.ecom.bookservice.rest.model.BookData;
import com.ecom.bookservice.service.impl.BookNewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bookinsert")
public class ImportController {

    @Autowired
    BookNewService bookNewService;

    @PostMapping
    public String insertBooks(@RequestBody List<BooksDetails> booksDetailsList) {

        Book book;
        for (BooksDetails b: booksDetailsList) {

            try {
                book = Book.builder()
                        .title(b.getTitle())
                        .authors(b.getAuthors())
                        .isbn(b.getIsbn())
                        .languageCode(b.getLanguage_code())
                        .build();
                double price = Double.parseDouble(b.getPrice());;
                double avgRating = Double.parseDouble(b.getAverage_rating());;
                int ratingCount = Integer.parseInt(b.getRatings_count());
                int id = Integer.parseInt(b.getBookID());
                book.setPrice(price);
                book.setAverageRating(avgRating);
                book.setRatingsCount(ratingCount);
                book.setBookID(id);
                bookNewService.addBook(book);
            }catch(Exception e){
                log.info("Exception e:> {}",e.getStackTrace());
                continue;
            }

        }


        return "Succes";
    }
}
