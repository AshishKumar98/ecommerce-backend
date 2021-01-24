package com.ecom.bookservice.rest.controller.dataimport;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BooksDetails {

    private String bookID;
    private String title;
    private String authors;
    private String average_rating;
    private String isbn;
    private String language_code;
    private String ratings_count;
    private String price;
    private String url;
}
