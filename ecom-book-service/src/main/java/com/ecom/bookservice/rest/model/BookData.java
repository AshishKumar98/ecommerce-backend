package com.ecom.bookservice.rest.model;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookData {


    @Size(min = 1, max = 5, message = "Book ID")
    private int bookID;

    @NotNull(message = "Book Title should not be null")
    @NotEmpty(message = "Book Title cannot be blank ")
    @Size(min = 3, max = 200, message = "Book Title should between 3 to 30 characters")
    private String title;

    @NotNull(message = "Author Name should not be null")
    @NotEmpty(message = "Author Name cannot be blank ")
    @Size(min = 3, max = 200, message = "Author Name should between 3 to 30 characters")
    private String authors;

    @NotNull(message = "Average Rating Name should not be null")
    @NotEmpty(message = "Average Rating cannot be blank ")
    @Size(min = 1, max = 6, message = "Average Rating should between 3 to 6 characters")
    private double average_rating;

    @NotNull(message = "ISBN should not be null")
    @NotEmpty(message = "ISBN cannot be blank ")
    @Size(min = 5, max = 200, message = "ISBN between 3 to 30 characters")
    private String isbn;

    @NotNull(message = "Language Code Name should not be null")
    @NotEmpty(message = "language Code cannot be blank ")
    @Size(min = 3, max = 200, message = "language Code should between 3 to 12 characters")
    private String language_code;

    @NotNull(message = "Rating Count should not be null")
    @NotEmpty(message = "Rating Count  cannot be blank ")
    @Size(min = 1, max = 200, message = "Rating Count Name should greater than 0 characters")
    private int ratings_count;

    @NotNull(message = "Price should not be null")
    @NotEmpty(message = "Price cannot be blank ")
    @Size(min = 1, max = 8, message = "Price should between 1 to 8 characters")
    private double price;

}
