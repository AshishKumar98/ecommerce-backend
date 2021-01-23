package com.ecom.bookservice.rest.controller;

import com.ecom.bookservice.enitity.Book;
import com.ecom.bookservice.mapper.JsonToEnityMapper;
import com.ecom.bookservice.rest.model.BookData;
import com.ecom.bookservice.rest.model.BookPage;
import com.ecom.bookservice.rest.model.BookSearchCriteria;
import com.ecom.bookservice.service.BookService;
import com.ecom.bookservice.service.impl.BookNewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bookapi")
public class BooksRestController {

    @Autowired
    BookService bookService;

    @Autowired
    BookNewService bookNewService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
                 produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BookData> createBook(@Valid @RequestBody List<BookData> bookList) {
        long startTime = System.currentTimeMillis();
        log.info("Create Book request: {}", bookList.size());
            List<Book> books = JsonToEnityMapper.bookDataToEntity(bookList);
            int i=0;
            for(Book b : books){
                bookService.save(b);
                i++;
                //log.info("Record inserted: {}",i);
            }
            log.info("createBook: latency {} Total Records Inserted: {}", System.currentTimeMillis() - startTime, i);
            return ResponseEntity.ok(JsonToEnityMapper.entityToBookData(books.get(i-1)));
    }


    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BookData> updateBook(@Valid @RequestBody BookData book) {
        long startTime = System.currentTimeMillis();
        log.info("Update Book request: {}", book);
        try {
            Book savedBook = bookService.save(JsonToEnityMapper.bookDataToEntity(book));
            log.info("Update: latency {}", System.currentTimeMillis() - startTime);
            return ResponseEntity.ok(JsonToEnityMapper.entityToBookData(savedBook));
        } catch (Exception e) {
            log.error("Error: {}", e.getStackTrace());
            return new ResponseEntity<>(book, HttpStatus.GATEWAY_TIMEOUT);
        }
    }

    //@GetMapping(name= "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @GetMapping("/{id}")
    public ResponseEntity<BookData> findBook(@PathVariable final int id) {
        long startTime = System.currentTimeMillis();
        log.info("get by Id Book : {}", id );
        Book savedBook = bookService.findByID(id);
        log.info("get By ID: latency {}", System.currentTimeMillis() - startTime);
        return ResponseEntity.ok(JsonToEnityMapper.entityToBookData(savedBook));

    }

    //@GetMapping(name= "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
 /*   @GetMapping
    public ResponseEntity<List<BookData>> allBooks() {
        long startTime = System.currentTimeMillis();
        log.info("get All Book : {}" );
        List<Book> bookList = bookService.findAll();
        log.info("GET All Books: latency {}", System.currentTimeMillis() - startTime);
        return ResponseEntity.ok(JsonToEnityMapper.entityToBookData(bookList));
    }*/

    /*@GetMapping()
    public ResponseEntity<List<BookData>> getPageableBooks(BookPage bookPage) {
        log.info("Paging request: {}",bookPage);
        Page<Book> bookList = bookNewService.getBooks(bookPage);
        return new ResponseEntity<>(JsonToEnityMapper.entityToBookData(bookList), HttpStatus.OK);
    }*/

   @GetMapping()
    public ResponseEntity<List<BookData>> getPageableBooks(BookPage bookPage,
                                                           BookSearchCriteria bookSearchCriteria) {
        log.info("Paging request-> Page:{}, Criteria:{}", bookPage, bookSearchCriteria);
        Page<Book> bookList = bookNewService.getBooks(bookPage, bookSearchCriteria);
        return new ResponseEntity<>(JsonToEnityMapper.entityToBookData(bookList), HttpStatus.OK);
    }


}
