package com.ecom.bookservice.enitity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "bookdetails")
@Builder
@NamedQuery(name="query_get_all_books", query = "select b from Book b")
public class Book {
    @Id
    private int bookID;

    @Column(name = "title")
    private String title;

    @Column(name="authors")
    private String authors;

    @Column(name="averagerating")
    private double averageRating;

    @Column(name="isbn")
    private String isbn;

    @Column(name="languagecode")
    private String languageCode;

    @Column(name="ratingscount")
    private Integer ratingsCount;

    @Column(name="price")
    private double price;
}
